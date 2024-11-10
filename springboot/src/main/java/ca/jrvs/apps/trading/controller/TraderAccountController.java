package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dom.Trader;
import ca.jrvs.apps.trading.dto.TraderProfileDTO;
import ca.jrvs.apps.trading.service.TraderAccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trader")
public class TraderAccountController {

  private final TraderAccountService traderAccountService;

  @Autowired
  public TraderAccountController(TraderAccountService traderAccountService) {
    this.traderAccountService = traderAccountService;
  }

  @ApiOperation("Create a new trader")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public TraderProfileDTO createTrader(@RequestBody Trader trader) {
    return traderAccountService.createTraderAndAccount(trader);
  }

  @ApiOperation("Deposit funds into trader account")
  @PutMapping(path = "/deposit/{traderId}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Double deposit(@PathVariable Integer traderId, @PathVariable Double amount) {
    return traderAccountService.deposit(traderId, amount);
  }

  @ApiOperation("Withdraw funds from trader account")
  @PutMapping(path = "/withdraw/{traderId}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Double withdraw(@PathVariable Integer traderId, @PathVariable Double amount) {
    return traderAccountService.withdraw(traderId, amount);
  }

  @ApiOperation("Delete a trader by their ID")
  @DeleteMapping(path = "/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTrader(@PathVariable Integer traderId) {
    traderAccountService.deleteTraderById(traderId);
  }

}
