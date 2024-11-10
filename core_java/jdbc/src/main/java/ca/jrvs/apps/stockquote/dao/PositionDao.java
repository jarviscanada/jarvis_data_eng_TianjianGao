package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.dto.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {

  private final Connection connection;

  public PositionDao(Connection connection) {
    this.connection = connection;
  }

  /**
   * Saves a given entity. Used for create and update
   *
   * @param entity - must not be null
   * @return The saved entity. Will never be null
   * @throws IllegalArgumentException - if ID is null
   */
  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    if (entity.getTicker() == null) {
      throw new IllegalArgumentException("Ticker cannot be null");
    }

    String upsertQuery = "INSERT INTO position (symbol, number_of_shares, value_paid) "
        + "VALUES (?, ?, ?) "
        + "ON CONFLICT (symbol) DO UPDATE SET "
        + "number_of_shares = EXCLUDED.number_of_shares, value_paid = EXCLUDED.value_paid";

    try (PreparedStatement statement = connection.prepareStatement(upsertQuery)) {
      statement.setString(1, entity.getTicker());
      statement.setInt(2, entity.getNumOfShares());
      statement.setDouble(3, entity.getValuePaid());

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Unable to save position", e);
    }
    return entity;
  }

  /**
   * Retrieves an entity by its id
   *
   * @param s - must not be null
   * @return Entity with the given id or empty optional if none found
   * @throws IllegalArgumentException - if id is null
   */
  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("Symbol cannot be null");
    }

    String selectQuery = "SELECT * FROM position WHERE symbol = ?";
    Position position = null;

    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
      preparedStatement.setString(1, s);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        position = extractPositionFromResultSet(resultSet);
      }
      } catch (SQLException e) {
        throw new RuntimeException("Unable to find position by ID", e);
      }

    return Optional.ofNullable(position);
  }

  /**
   * Retrieves all entities
   *
   * @return All entities
   */
  @Override
  public Iterable<Position> findAll() {
    List<Position> positions = new ArrayList<>();
    String selectQuery = "SELECT * FROM position";

    try(Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(selectQuery);

      while (resultSet.next()) {
        Position position = extractPositionFromResultSet(resultSet);
        positions.add(position);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Unable to retrieve all positions", e);
    }
      return positions;
    }

  private Position extractPositionFromResultSet(ResultSet resultSet) throws SQLException {
    Position position = new Position();
    position.setTicker(resultSet.getString("symbol"));
    position.setNumOfShares(resultSet.getInt("number_of_shares"));
    position.setValuePaid(resultSet.getDouble("value_paid"));
    return position;
  }

  /**
   * Deletes the entity with the given id. If the entity is not found, it is silently ignored
   *
   * @param s - must not be null
   * @throws IllegalArgumentException - if id is null
   */
  @Override
  public void deleteById(String s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("Symbol cannot be null");
    }

    String deleteQuery = "DELETE FROM position WHERE symbol = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
      preparedStatement.setString(1, s);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Unable to delete position by ID", e);
    }
  }

  /**
   * Deletes all entities managed by the repository
   */
  @Override
  public void deleteAll() {
    String deleteAllQuery = "DELETE FROM position";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(deleteAllQuery);
    } catch (SQLException e) {
      throw new RuntimeException("Unable to delete all positions", e);
    }
  }
}