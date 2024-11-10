package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.dto.GlobalQuote;
import ca.jrvs.apps.stockquote.dto.Quote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDao implements CrudDao<Quote, String> {

  private final Connection connection;

  public QuoteDao(Connection connection) {
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
  public Quote save(Quote entity) throws RuntimeException {
    GlobalQuote globalQuote = entity.getGlobalQuote();

    if (globalQuote.getSymbol() == null) {
      throw new IllegalArgumentException("Ticker cannot be null");
    }

    String upsert = "INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, "
        + "previous_close, change, change_percent, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
        + "ON CONFLICT (symbol) DO UPDATE SET "
        + "open = EXCLUDED.open, high = EXCLUDED.high, low = EXCLUDED.low, price = EXCLUDED.price, "
        + "volume = EXCLUDED.volume, latest_trading_day = EXCLUDED.latest_trading_day, "
        + "previous_close = EXCLUDED.previous_close, change = EXCLUDED.change, "
        + "change_percent = EXCLUDED.change_percent, timestamp = EXCLUDED.timestamp";

    try (PreparedStatement ps = connection.prepareStatement(upsert)) {
      ps.setString(1, globalQuote.getSymbol());
      ps.setDouble(2, globalQuote.getOpen());
      ps.setDouble(3, globalQuote.getHigh());
      ps.setDouble(4, globalQuote.getLow());
      ps.setDouble(5, globalQuote.getPrice());
      ps.setDouble(6, globalQuote.getVolume());
      ps.setDate(7, globalQuote.getLatestTradingDay());
      ps.setDouble(8, globalQuote.getPreviousClose());
      ps.setDouble(9, globalQuote.getChange());
      ps.setString(10, globalQuote.getChangePercent());
      ps.setTimestamp(11, entity.getTimestamp());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Unable to save quote", e);
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
  public Optional<Quote> findById(String s) throws IllegalArgumentException {
    if (s == null) {
      return Optional.empty();
    }

    String selectQuery = "SELECT * FROM quote WHERE symbol = ?";
    Quote quote = null;

    try(PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
      preparedStatement.setString(1, s);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        quote = extractQuoteFromResultSet(rs);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Unable to find quote by ID", e);
    }

    return Optional.ofNullable(quote);
  }

  /**
   * Retrieves all entities
   *
   * @return All entities
   */
  @Override
  public Iterable<Quote> findAll() {
    List<Quote> quotes = new ArrayList<>();
    String selectAllQuery = "SELECT * FROM quote";

    try(Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(selectAllQuery);

      while (resultSet.next()) {
        Quote quote = extractQuoteFromResultSet(resultSet);
        quotes.add(quote);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Unable to find all quotes", e);
    }

    return quotes;
  }

  /**
   * Helper method to extract a Quote object from a ResultSet
   * @param resultSet results from executing queries
   * @return Quote
   */
  private Quote extractQuoteFromResultSet(ResultSet resultSet) throws SQLException {
    if (resultSet == null) {
      throw new IllegalArgumentException("ResultSet cannot be null");
    }
    Quote quote = new Quote();
    GlobalQuote globalQuote = new GlobalQuote();
    globalQuote.setSymbol(resultSet.getString("symbol"));
    globalQuote.setOpen(resultSet.getDouble("open"));
    globalQuote.setHigh(resultSet.getDouble("high"));
    globalQuote.setLow(resultSet.getDouble("low"));
    globalQuote.setPrice(resultSet.getDouble("price"));
    globalQuote.setVolume(resultSet.getDouble("volume"));
    globalQuote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
    globalQuote.setPreviousClose(resultSet.getDouble("previous_close"));
    globalQuote.setChange(resultSet.getDouble("change"));
    globalQuote.setChangePercent(resultSet.getString("change_percent"));
    quote.setGlobalQuote(globalQuote);
    quote.setTimestamp(resultSet.getTimestamp("timestamp"));
    return quote;
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
      throw new IllegalArgumentException("ID cannot be null");
    }

    String deleteQuery = "DELETE FROM quote WHERE symbol = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
      preparedStatement.setString(1, s);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Unable to delete quote by ID", e);
    }
  }

  /**
   * Deletes all entities managed by the repository
   */
  @Override
  public void deleteAll() {
    String deleteAllQuery = "DELETE FROM quote";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(deleteAllQuery);
    } catch (SQLException e) {
      throw new RuntimeException("Unable to delete all quotes", e);
    }
  }
}

