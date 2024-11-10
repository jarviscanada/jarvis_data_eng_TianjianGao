package ca.jrvs.apps.stockquote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;

public class GlobalQuote {
  @JsonProperty("01. symbol")
  private String symbol;

  @JsonProperty("02. open")
  private double open;

  @JsonProperty("03. high")
  private double high;

  @JsonProperty("04. low")
  private double low;

  @JsonProperty("05. price")
  private double price;

  @JsonProperty("06. volume")
  private double volume;

  @JsonProperty("07. latest trading day")
  private Date latestTradingDay;

  @JsonProperty("08. previous close")
  private double previousClose;

  @JsonProperty("09. change")
  private double change;

  @JsonProperty("10. change percent")
  private String changePercent;

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setLatestTradingDay(Date latestTradingDay) {
    this.latestTradingDay = latestTradingDay;
  }

  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  public void setChange(double change) {
    this.change = change;
  }

  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  public String getSymbol() {
    return symbol;
  }

  public double getOpen() {
    return open;
  }

  public double getHigh() {
    return high;
  }

  public double getLow() {
    return low;
  }

  public double getVolume() {
    return volume;
  }

  public double getPrice() {
    return price;
  }

  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  public double getPreviousClose() {
    return previousClose;
  }

  public double getChange() {
    return change;
  }

  public String getChangePercent() {
    return changePercent;
  }
}
