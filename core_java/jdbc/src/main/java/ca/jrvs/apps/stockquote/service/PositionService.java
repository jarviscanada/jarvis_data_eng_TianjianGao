package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.GlobalQuote;
import ca.jrvs.apps.stockquote.dto.Position;
import ca.jrvs.apps.stockquote.dto.Quote;
import java.io.IOException;
import java.util.Optional;

public class PositionService {
  private final PositionDao positionDao;
  private final QuoteService quoteService;

  public PositionService(PositionDao positionDao, QuoteService quoteService) {
    this.positionDao = positionDao;
    this.quoteService = quoteService;
  }

  /**
   * Processes a buy order and updates the database accordingly
   * @param symbol ticker symbol, e.g. "AAPL"
   * @param numberOfShares number of shares to purchase
   * @param price price per share
   * @return the position in database after processing the buy
   */
  public Position buy(String symbol, int numberOfShares, double price)
      throws IOException, InterruptedException {
    // Validate the ticker symbol and fetch the quote
    Optional<Quote> quote = quoteService.getQuote(symbol);
    GlobalQuote globalQuote = quote.map(Quote::getGlobalQuote).orElse(null);
    if (globalQuote == null) {
      throw new IllegalArgumentException("Invalid symbol: " + symbol);
    }

    // Check if the requested number of shares is available
    if (numberOfShares > globalQuote.getVolume()) {
      throw new IllegalArgumentException("Cannot buy more than available volume. Requested: "
          + numberOfShares + ", Available: " + globalQuote.getVolume());
    }

    // Check if the price matches the current market price
    if (price != globalQuote.getPrice()) {
      throw new IllegalArgumentException("Price mismatch. Market price is: " + globalQuote.getPrice());
    }

    // Check if the position already exists
    Optional<Position> optionalPosition = positionDao.findById(symbol);
    Position position = optionalPosition.orElse(null);

    if (optionalPosition.isPresent()) {
      // Update the existing position
      int newNumberOfShares = position.getNumOfShares() + numberOfShares;
      double totalValuePaid = position.getValuePaid() + (numberOfShares * price);

      position.setNumOfShares(newNumberOfShares);
      position.setValuePaid(totalValuePaid);
    } else {
      position = new Position();
      position.setTicker(symbol);
      position.setNumOfShares(numberOfShares);
      position.setValuePaid(numberOfShares * price);
    }

    // Save the position to the database
    return positionDao.save(position);
  }

  /**
   * Sell all shares of the given ticker symbol
   * @param symbol ticker symbol, e.g. "GOOG"
   */
  public void sellAll(String symbol) {
    Optional<Position> optionalPosition = positionDao.findById(symbol);
    if (optionalPosition.isPresent()) {
      // Delete the position from the database
      positionDao.deleteById(symbol);
    } else {
      throw new IllegalArgumentException("Position with symbol " + symbol + " not found.");
    }
  }
}
