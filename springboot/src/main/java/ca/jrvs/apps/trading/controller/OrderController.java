package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dom.SecurityOrder;
import ca.jrvs.apps.trading.dto.SecurityOrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @ApiOperation("Execute a market order")
  @PostMapping(path = "/marketOrder", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public SecurityOrderDTO executeMarketOrder(@RequestBody SecurityOrderDTO order) {
    return orderService.executeMarketOrder(order);
  }
}
