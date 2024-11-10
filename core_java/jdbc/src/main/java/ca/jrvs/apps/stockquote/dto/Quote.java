package ca.jrvs.apps.stockquote.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
  @JsonProperty("Global Quote")
  private GlobalQuote globalQuote;

  private Timestamp timestamp;

  public GlobalQuote getGlobalQuote() {
    return globalQuote;
  }

  public void setGlobalQuote(GlobalQuote globalQuote) {
    this.globalQuote = globalQuote;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }
}
