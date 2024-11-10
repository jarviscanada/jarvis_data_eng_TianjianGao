package ca.jrvs.apps.trading.dao;

// Deserializes the Alpha Quote JSON into an AlphaQuote domain object.


import ca.jrvs.apps.trading.dom.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class MarketDataDAO {

  // Alpha Vantage API URL and API_KEY (should store it in App.config)
  private static final String API_URL = "https://www.alphavantage.co/query";
  private static final String API_KEY = "demo";

  // ObjectMapper for parsing JSON
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Get an AlphaQuote by ticker.
   *
   * @param ticker the stock ticker
   * @return Optional<AlphaQuote> containing the quote data if available
   * @throws IllegalArgumentException if the given ticker is invalid
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP request fails
   */
  public Optional<Quote> findById(String ticker) {
    if (ticker == null || ticker.isEmpty()) {
      throw new IllegalArgumentException("Ticker cannot be null or empty");
    }

    // Build the URL for Alpha Vantage API request
    String uri = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("function", "TIME_SERIES_DAILY")
        .queryParam("symbol", ticker)
        .queryParam("apikey", API_KEY)
        .toUriString();

    // Execute the HTTP GET request and parse the response
    return executeHttpGet(uri).map(this::parseAlphaQuote);
  }

  /**
   * Get quotes for a list of tickers
   *
   * @param tickers is a list of stock tickers
   * @return a list of AlphaQuote objects
   * @throws IllegalArgumentException if any ticker is invalid
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP request fails
   */
  public List<Quote> findAllById(Iterable<String> tickers) {
    List<Quote> quotes = new ArrayList<>();
    for (String ticker : tickers) {
      findById(ticker).ifPresent(quotes::add);
    }
    return quotes;
  }

  /**
   * Execute a GET request and return HTTP entity/body as a string
   *
   * @param url resource URL
   * @return Optional<String> containing the response body or Optional.empty() for 404 responses
   * @throws org.springframework.dao.DataRetrievalFailureException if HTTP request fails or receives an unexpected status code
   */
  private Optional<String> executeHttpGet(String uri) {
    HttpClient client = getHttpClient();
    HttpGet request = new HttpGet(uri);

    try {
      HttpResponse response = client.execute(request);
      int statusCode = response.getStatusLine().getStatusCode();

      // Successfully received data
      if (statusCode == 200) {
        HttpEntity entity = response.getEntity();
        return Optional.of(EntityUtils.toString(entity));
      } else if (statusCode == 404) { // Ticker not found
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
   *
   * @return a HttpClient
   */
  private HttpClient getHttpClient() {
    return HttpClients.createDefault();
  }

  /**
   * Parse the JSON response and convert it to an AlphaQuote object.
   *
   * @param json the JSON string to parse
   * @return the parsed AlphaQuote object
   * @throws DataRetrievalFailureException if there is an issue parsing the JSON
   */
  private Quote parseAlphaQuote(String json) throws IOException {
    try {
      JsonNode root = objectMapper.readTree(json);

      JsonNode timeSeries = root.path("Time Series (Daily)");

      if (timeSeries.isMissingNode()) {
        throw new DataRetrievalFailureException("Invalid response: missing 'Time Series (Daily)' node");
      }

      // Get the most recent date entry in the time series
      String latestQuote = timeSeries.fieldNames().next();
      JsonNode latestQuote = timeSeries.path(latestDate);

      if (latestQuote.isMissingNode()) {
        throw new DataRetrievalFailureException("Invalid response: missing 'Latest Quote' node");
      }

      // Create and populate
    }
  }
}
