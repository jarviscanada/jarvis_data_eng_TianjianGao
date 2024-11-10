package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dto.PortfolioDTO;
import ca.jrvs.apps.trading.dto.TraderProfileDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  private final DashboardService dashboardService;

  @ApiOperation("Show trader profile by trader ID")
  @GetMapping(path = "/profile/traderId/{traderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public TraderProfileDTO getTraderProfile(@PathVariable Integer traderId) {
    return dashboardService.getTraderProfile(traderId);
  }

  @ApiOperation("Show portfolio by trader ID")
  @GetMapping(path = "/portfolio/traderId/{traderId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public PortfolioDTO getPortfolio(@PathVariable Integer traderId) {
    return dashboardService.getPortfolio(traderId);
  }
}
