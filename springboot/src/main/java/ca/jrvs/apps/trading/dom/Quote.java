package ca.jrvs.apps.trading.dom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quote")
public class Quote {

  @Id
  @Column(name = "ticker", nullable = false)
  private String ticker;

  @Column(name = "last_price", nullable = false)
  private double lastPrice;

  @Column(name = "bid_price", nullable = false)
  private double bidPrice;

  @Column(name = "ask_price", nullable = false)
  private double askPrice;

  @Column(name = "ask_size", nullable = false)
  private int askSize;

  // Getters and setters

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(double askPrice) {
    this.askPrice = askPrice;
  }

  public int getAskSize() {
    return askSize;
  }

  public void setAskSize(int askSize) {
    this.askSize = askSize;
  }
}
