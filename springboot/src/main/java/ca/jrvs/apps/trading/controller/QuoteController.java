package ca.jrvs.apps.trading;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.awt.PageAttributes.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//- Set the root path for all endpoints to `http://localhost:8080/quote`
//- Determine the dependencies of the controller and inject them properly
//- Set the path for the `getQuote` method to `/iex/ticker/{ticker}`
//- Determine the proper annotations required to utilize the method params
//- Set the response status for the `getQuote` method to 200 (OK)
//- Handle exceptions
//- The following code snippet is to help get you started

@Api(value = "quote", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping("/quote")
public class QuoteController {

  private QuoteService quoteService;

  @Autowired
  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @ApiOperation(value = "Show AlphaQuote", notes = "Show AlphaQuote for a given ticker/symbol")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "ticket is not found")})
  @GetMapping(path = "/alpha/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public AlphaQuote getQuote(String ticker) {
    try {
      return quoteService.findAlphaQuoteByTicker(ticker);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
