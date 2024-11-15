package ca.jrvs.apps.trading;

/**
 * Configure DataSource and JDBCTemplate
 */

import ca.jrvs.apps.trading.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class,
    DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Application implements CommandLineRunner {
  private Logger logger = LoggerFactory.getLogger(Application.class);

  @Value("${app.init.dailyList}")
  private String[] initDailyList;

  @Autowired
  private QuoteService quoteService;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.run(args);
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("Starting the application and initializing the daily list...");

    // Initialize daily list of tickers (quotes)
    for (String ticker : initDailyList) {
      try {
        logger.info("Adding ticker: {}", ticker);
        quoteService.addQuote(ticker);
      } catch (Exception e) {
        logger.error("Failed to add ticker: {}", ticker, e);
      }
    }

    logger.info("Application started successfully.");
  }
}