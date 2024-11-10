package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dto.Quote;
import ca.jrvs.apps.trading.dto.QuoteDTO;
import ca.jrvs.apps.trading.service.QuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// Functionalities
// Receives HTTP requests and calls the QuoteService methods
// Returns the AlphaQuote to the user via HTTP

//- Set the root path for all endpoints to `http://localhost:8080/quote`
//- Determine the dependencies of the controller and inject them properly
//- Set the path for the `getQuote` method to `/iex/ticker/{ticker}`
//- Determine the proper annotations required to utilize the method params
//- Set the response status for the `getQuote` method to 200 (OK)
//- Handle exceptions
//- The following code snippet is to help get you started

// GET /alpha/ticker/{ticker}
// PUT
// POST
// DELETE

@Api(value = "quote", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/quote")
public class QuoteController {

  private final QuoteService quoteService;

  // Dependency injection of QuoteService using constructor
  @Autowired
  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  // Endpoint to get a quote from Alpha Vantage by ticker
  @ApiOperation(value = "Show AlphaQuote", notes = "Show AlphaQuote for a given ticker/symbol")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "ticket is not found")})
  @GetMapping(path = "/alpha/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  public AlphaQuote getQuote(@PathVariable String ticker) {
    try {
      return quoteService.findAlphaQuoteByTicker(ticker);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticker: " + ticker, e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found for ticker: " + ticker, e);
    }
  }

  // Endpoint to update the quote table using Alpha Vantage data
  @ApiOperation(value = "Update quote table using AlphaVantage data",
      notes = "Update all quotes in the quote table. Use AlphaVantage trading API as market data source.")
  @PutMapping(path = "/alphaMarketData")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> updateMarketData() {
    try {
      return quoteService.updateMarketData();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  // Endpoint to show the daily list of quotes
  @ApiOperation(value = "Show the dailyList", notes = "Show dailyList for this trading system.")
  @GetMapping(path = "/dailyList", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<Quote> getDailyList() {
    try {
      return quoteService.findAllQuotes();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error showing dailyList: " + e);
    }
  }

 // Endpoint to add a new ticker to the daily list
  @ApiOperation(value = "Add a new ticker", notes = "Add a new ticker to the dailyList.")
  @PostMapping(path = "/tickerId/{tickerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Quote addTicker(@PathVariable String tickerId) {
    try {
      return quoteService.addQuote(tikerId);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ticker: " + tickerId, e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding ticker: " + tickerId, e);
    }
  }

  // Endpoint to manually update a quote in the table
  @ApiOperation(value = "Update a given quote", notes = "Update a given quote manually in the quote table")
  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public Quote updateQuote(@RequestBody Quote quote) {
    try {
      return quoteService.saveQuote(quote);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid quote: " + quote, e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Quote update failed", e);
    }
  }

}
