package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import java.io.IOException;
import java.util.Arrays;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.aspectj.lang.annotation.Before;

public class MarketDataDaoIntTest {

  private MarketDataDAO dao;

  @Before
  public void init() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(200);
    cm.setDefaultMaxPerRoute(20);
    MarketDataConfig config = new MarketDataConfig();
    config.setHost("localhost"); //TODO
    config.setToken(System.getenv("TRADING_TOKEN"));

    dao = new MarketDataDAO(cm, config);
  }

  @Test
  public void findAlphaQuoteByTickers() throws IOException {
    //happy path
    List<AlphaQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
    assertEquals(2, quoteList.size);
    assertEquals("AAPL", quoteList.get(0).getSymbol());

    //sad path
    try {
      dao.findAllById(Arrays.asList("AAPL", "FB"));
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void findByTicker() {
    String ticker = "AAPL";
    AlphaQuote quote = dao.findById(ticker).get();
    assertEquals(ticker, quote.getSymbol());
  }
}
