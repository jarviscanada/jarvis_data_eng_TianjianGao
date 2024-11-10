package ca.jrvs.apps.stockquote.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKeyReader {
  public static String getApiKey() {
    Properties props = new Properties();
    try (InputStream in = ApiKeyReader.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (in == null) {
        System.out.println("Cannot find config file");
        return null;
      }
      // Load property file
      props.load(in);
//      System.out.println(props.getProperty("x-rapidapi-key"));
      // return apiKey
      return props.getProperty("x-rapidapi-key");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
