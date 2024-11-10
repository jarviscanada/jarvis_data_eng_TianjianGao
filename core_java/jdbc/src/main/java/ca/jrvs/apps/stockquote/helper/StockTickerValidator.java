package ca.jrvs.apps.stockquote.dao.helper;

import static ca.jrvs.apps.stockquote.dao.helper.ApiKeyReader.getApiKey;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockTickerValidator {
  static final Logger logger = LoggerFactory.getLogger(StockTickerValidator.class);
  private static final String TICKER_REGEX = "^[A-Z]{1,5}";
  private static final String apiKey = getApiKey();

  public static boolean isValidTickerFormat(String ticker) {
    return ticker.matches(TICKER_REGEX);
  }

  public static boolean isValidSymbol(String symbol) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=SYMBOL_SEARCH&keywords="+symbol))
        .header("X-RapidAPI-Key", apiKey)
        .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
    try {
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
      return response.toString().contains(symbol);
    } catch (InterruptedException | IOException e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  public static void main(String[] args) {
    String ticker = "AAPL";
    System.out.println(isValidTickerFormat(ticker));
    System.out.println(isValidSymbol(ticker));
  }
}
