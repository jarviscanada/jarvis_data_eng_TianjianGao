package ca.jrvs.apps.trading.service;

// The QuoteService calls MarketDataDao methods which queries Quote data from Alpha REST API
// (in JSON format)


import ca.jrvs.apps.trading.dao.MarketDataDAO;
import ca.jrvs.apps.trading.dao.QuoteDAO;
import ca.jrvs.apps.trading.dto.Quote;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

  private final MarketDataDAO marketDataDao;
  private final QuoteDAO quoteDao;

  @Autowired
  public QuoteService(MarketDataDAO marketDataDao, QuoteDAO quoteDao) {
    this.marketDataDao = marketDataDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Find an AlphaQuote by ticker
   * @param ticker the stock ticker
   * @return ALphaQuote objet
   * throws IllegalArgumentException if ticker is invalid
   */
  public AlphaQuote findAlphaQuoteByTicker(String ticker) {
    validateTicker(ticker);

    // Fetch quote from Alpha Vantage API via MarketDataDao
    Optional<AlphaQuote> optionalAlphaQuote = marketDataDao.findById(ticker);

    if (!optionalAlphaQuote.isPresent()) {
      throw new IllegalArgumentException("Invalid ticker: " + ticker);
    }

    return optionalAlphaQuote.get();
  }

  /**
   * Find all quotes in the system (dailyList).
   * @return List of quotes
   */
  public List<Quote> findAllQuotes() {
    try {
      return quoteDao.findAll();
    } catch (DataAccessException e) {
      throw new RuntimeException("Failed to retrieve quotes from the database", e);
    }
  }

  /**
   * Add a new quote by ticker if it doesn't exist in the database.
   * @param ticker stock ticker
   * @return the created quote
   * @throws IllegalArgumentException if the ticker is invalid
   */
  public Quote addQuote(String  ticker) {
    validateTicker(ticker);

    // Check if ticker already exists
    if (quoteDao.existsById(ticker)) {
      throw new IllegalArgumentException("Ticker already exists: " + ticker);
    }

    // Fetch data from Alpha Vantage to create the quote
    AlphaQuote alphaQuote = findAlphaQuoteByTicker(ticker);

    // Convert AlphaQuote to Quote entity and save it to DB
    Quote quote = buildQuoteFromAlphaQuote(alphaQuote);

    return quoteDao.save(quote);
  }

  /**
   * Update all quotes in the system by fetching fresh data from Alpha Vantage
   * @return List of updated quotes
   */
  public List<Quote> updateMarketData() {
    List<Quote> quotes = findAllQuotes();
    for (Quote quote : quotes) {
      AlphaQuote alphaQuote = findAlphaQuoteByTicker(quote.getTicker());
      updateQuoteFromAlphaQuote(quote, alphaQuote);
      quoteDao.save(quote);
    }
  }
}
