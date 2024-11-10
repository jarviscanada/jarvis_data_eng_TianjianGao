package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.dto.Quote;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.util.Optional;
import java.util.Scanner;

//Considerations/features to keep in mind:
//
// - We want to make sure we are working with the most updated stock quote data
// - We want to see what the current value of our position is vs what we paid into it to determine if we will make a profit by selling at this time
// - We want to be able to be able to see information on a given stock before choosing to buy it
// - We want our application to continue running even if inputs are invalid
// - As the developer and designer, you get to choose the flow of the application. Be creative!

public class StockQuoteController {
  private final QuoteService quoteService;
  private PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  /**
   * User interface for our application
   */
  public void initClient() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Enter quote symbol (or 'exit' to quit): ");
      String symbol = scanner.nextLine();
      if ("exit".equalsIgnoreCase(symbol)) {
        break;
      }

      // Fetch and display the quote
      Optional<Quote> quoteOptional = quoteService.getQuote(symbol);
      if (quoteOptional.isPresent()) {
        Quote quote = quoteOptional.get();
        System.out.println("Quote: " + quote);

        // Save the quote to the database
        quoteService.saveQuote(quote);
      } else {
        System.out.println("Quote not found: " + symbol);
      }
      scanner.close();
    }
  }

}
