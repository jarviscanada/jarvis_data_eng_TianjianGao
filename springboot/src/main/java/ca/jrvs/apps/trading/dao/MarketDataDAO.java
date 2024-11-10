package ca.jrvs.apps.trading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.util.UriComponentsBuilder;

public class MarketDataDao {

  /**
   * Get an AlphaQuote
   *
   * @param ticker
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP request failed
   */
  public Optional<AlphaQuote> findById(String ticker) {
    if (ticker == null || ticker.isEmpty()) {
      throw new IllegalArgumentException("Ticker cannot be null or empty");
    }

    String uri = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("function", "TIME_SERIES_DAILY")
        .queryParam("symbol", ticker)
        .queryParam("apikey", API_KEY)
        .toUriString();

    return executeHttpGet(uri).map(this::parseAlphaQuote);
  }

  /**
   * Get quotes from Alpha Vantage
   * @param tickers is a list of tickers
   * @return a list of AlphaQuote objects
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP request failed
   */
  public List<AlphaQuote> findAllById(Iterable<String> tickers) {
    List<AlphaQuote> quotes = new ArrayList<>();
    for (String ticker : tickers) {
      findById(ticker).ifPresent(quotes::add);
    }
    return quotes;
  }

  /**
   * Execute a GET request and return HTTP entity/body as a string
   * Tip: use EntityUtils.toString to process HTTP entity
   *
   * @param url resource URL
   * @return http response body or Optional.empty for 404 response
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  private Optional<String> executeHttpGet(String uri) {
    HttpClient client = getHttpClient();
    HttpGet request = new HttpGet(uri);

    try {
      HttpResponse response = client.execute(request);
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != 200) {
        HttpEntity entity = response.getEntity();
        return Optional.of(EntityUtils.toString(entity));
      } else if (statusCode == 404) {
        return Optional.empty();
      } else {
        throw new DataRetrievalFailureException("Unexpected response code: " + statusCode);
      }
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Error executing request: " + uri, e);
    }
  }

  /**
   * Borrow a HTTP client from the HttpClientConnectionManager
   * @return a HttpClient
   */
  private HttpClient getHttpClient() {
    return HttpClients.createDefault();
  }

  /**
   * Parse the JSON response and convert it to an AlphaQuote object.
   * This is a placeholder method and needs to be implemented according to the JSON structure.
   */
  private AlphaQuote parseAlphaQuote(String json) {
    // Implement JSON parsing logic here
    // For example, using Jackson or Gson to convert JSON into AlphaQuote object
    // This is a placeholder and needs to be implemented
    return new AlphaQuote();
  }
}
