package ca.jrvs.apps.trading.dto;

public class PositionDTO {

  private String ticker;
  private Integer size;  // Number of shares held

  // Getters and Setters
  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
