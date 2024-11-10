package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.Quote;
import ca.jrvs.apps.stockquote.helper.QuoteHttpHelper;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuoteService {
  private final QuoteDao quoteDao;
  private final QuoteHttpHelper quoteHttpHelper;

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  public QuoteService(QuoteDao quoteDao, QuoteHttpHelper quoteHttpHelper) {
    this.quoteDao = quoteDao;
    this.quoteHttpHelper = quoteHttpHelper;
  }

  /**
   * Fetches latest quote data from Alpha Vantage API endpoint
   * @param symbol e.g. "AAPL"
   * @return latest quote stored in Quote object or empty optional if ticker symbol is not found
   */
  public Optional<Quote> getQuote(String symbol) {
    try {
      Quote quote = quoteHttpHelper.fetchQuoteInfo(symbol);
      return Optional.of(quote);
    } catch (IOException | InterruptedException e) {
      logger.error("Error fetching quote for symbol: {}", symbol, e);
      return Optional.empty();
    } catch (IllegalArgumentException e) {
      logger.error("Invalid ticker symbol: {}", symbol, e);
      return Optional.empty();
    }
  }

  public void saveQuote(Quote quote) {
    try {
      quoteDao.save(quote);
    } catch (Exception e) {
      logger.error("Error saving quote: {}", quote, e);
      throw new RuntimeException("Failed to save quote", e);
    }
  }
}
