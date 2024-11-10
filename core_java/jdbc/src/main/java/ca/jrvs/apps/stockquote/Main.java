package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.helper.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws FileNotFoundException {
    Map<String, String> properties = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/stockquote.properties"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] tokens = line.split(":");
        properties.put(tokens[0], tokens[1]);
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }

    try {
      Class.forName(properties.get("db-class"));
    } catch (ClassNotFoundException e) {
      logger.error(e.getMessage(), e);
    }
    OkHttpClient client = new OkHttpClient();
    String url = "jdbc:postgresql://" + properties.get("server") + ":" + properties.get("port") + "/" + properties.get("database");
    try (Connection c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"))) {
      QuoteDao quoteDao = new QuoteDao(c);
      PositionDao positionDao = new PositionDao(c);
      QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(client, new ObjectMapper());
      QuoteService quoteService = new QuoteService(quoteDao, quoteHttpHelper);
      PositionService positionService = new PositionService(positionDao, quoteService);
      StockQuoteController stockQuoteController = new StockQuoteController(quoteService, positionService);
      stockQuoteController.initClient();
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
