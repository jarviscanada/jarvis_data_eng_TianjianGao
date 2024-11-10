package ca.jrvs.apps.trading.dto;

import java.util.List;

public class PortfolioDTO {

  private Integer traderId;
  private Double accountBalance;
  private List<PositionDTO> positions;

  // Getters and Setters
  public Integer getTraderId() {
    return traderId;
  }

  public void setTraderId(Integer traderId) {
    this.traderId = traderId;
  }

  public Double getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(Double accountBalance) {
    this.accountBalance = accountBalance;
  }

  public List<PositionDTO> getPositions() {
    return positions;
  }

  public void setPositions(List<PositionDTO> positions) {
    this.positions = positions;
  }
}
