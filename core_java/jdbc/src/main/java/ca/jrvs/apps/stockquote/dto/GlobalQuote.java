package ca.jrvs.apps.stockquote.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQuote {

  private String ticker;
  private double open;
  private double high;
  private double low;
  private double volume;
  private double price;
  private Date lastTradingDay;
  private double previousClose;
  private double change;
  private String changePercent;
  private Timestamp timestamp;

  public void setTicker(String ticker) {
    this.ticker = ticker;
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

  public void setLastTradingDay(Date lastTradingDay) {
    this.lastTradingDay = lastTradingDay;
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

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public String getTicker() {
    return ticker;
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

  public Date getLastTradingDay() {
    return lastTradingDay;
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

  public Timestamp getTimestamp() {
    return timestamp;
  }
}
