package ca.jrvs.apps.stockquote.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Position {
  private String ticker;
  private int numOfShares;
  private double valuePaid; // total amount paid for shares

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getNumOfShares() {
    return numOfShares;
  }

  public void setNumOfShares(int numOfShares) {
    this.numOfShares = numOfShares;
  }

  public double getValuePaid() {
    return valuePaid;
  }

  public void setValuePaid(double valuePaid) {
    this.valuePaid = valuePaid;
  }
}
