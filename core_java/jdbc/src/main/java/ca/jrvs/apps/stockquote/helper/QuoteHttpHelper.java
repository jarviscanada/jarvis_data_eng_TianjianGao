package ca.jrvs.apps.stockquote.helper;

import static ca.jrvs.apps.stockquote.helper.ApiKeyReader.getApiKey;

import ca.jrvs.apps.stockquote.dto.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuoteHttpHelper {

  static final Logger logger = LoggerFactory.getLogger(QuoteHttpHelper.class);

  private final String apiKey;
  private final HttpClient httpClient;
  private final ObjectMapper mapper;

  public QuoteHttpHelper(HttpClient httpClient, ObjectMapper mapper) {
    this.apiKey = getApiKey();
    this.httpClient = httpClient;
    this.mapper = mapper;
  }
  /**
   * Fetch latest quote data from Alpha Vantage endpoint
   * @param symbol symbol
   * @return Quote with latest data
   * @throws IllegalArgumentException - if no data was found for the given symbol
   * @throws IOException - if an I/O error occurs
   * @throws InterruptedException - if the operation is interrupted
   */
  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json"))
        .header("X-RapidAPI-Key", apiKey)
        .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() != 200) {
      throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
//      throw new IOException("Failed to fetch quote info: " + response.body());
    }

    String responseBody = response.body();
    logger.info("Response body: " + responseBody);

    try {
      // Deserialize JSON object using Jackson
      Quote quote = mapper.readValue(responseBody, Quote.class);
      // Check for valid ticker symbol
      // Todo: maybe check for valid ticker symbol means other stuff?
      if (quote == null || quote.getGlobalQuote().getSymbol() == null) {
        throw new IllegalArgumentException("Invalid ticker symbol: " + symbol);
      }
      Instant now = Instant.now();
      quote.setTimestamp(Timestamp.from(now));
      return quote;
    } catch (Exception e) {
      logger.error("Failed to parse quote: " + e.getMessage());
      throw new IOException("Failed to parse quote: " + e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException {
    HttpClient httpClient = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    QuoteHttpHelper helper = new QuoteHttpHelper(httpClient, mapper);
    try {
      Quote quote = helper.fetchQuoteInfo("NVDA");
    } catch (Exception e) {
      logger.error("Error fetching quote{}", e.getMessage());
    }
  }

}
