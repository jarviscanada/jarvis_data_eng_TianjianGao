package ca.jrvs.apps.trading.dto;

public class TraderProfileDTO {

  private TraderDTO trader;
  private AccountDTO account;

  // Getters and Setters
  public TraderDTO getTrader() {
    return trader;
  }

  public void setTrader(TraderDTO trader) {
    this.trader = trader;
  }

  public AccountDTO getAccount() {
    return account;
  }

  public void setAccount(AccountDTO account) {
    this.account = account;
  }
}
