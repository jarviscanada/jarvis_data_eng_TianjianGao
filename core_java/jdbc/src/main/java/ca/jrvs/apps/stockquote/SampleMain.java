package ca.jrvs.apps.stockquote;

//public class SampleMain {
//
//  public static void main(String[] args) {
//    Map<String, String> properties = new HashMap<>();
//    try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/properties.txt"))) {
//      String line;
//      while ((line = br.readLine()) != null) {
//        String[] tokens = line.split(":");
//        properties.put(tokens[0], tokens[1]);
//      }
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      Class.forName(properties.get("db-class"));
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//    OkHttpClient client = new OkHttpClient();
//    String url = "jdbc:postgresql://"+properties.get("server")+":"+properties.get("port")+"/"+properties.get("database");
//    try (Connection c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"))) {
//      QuoteDao qRepo = new QuoteDao(c);
//      PositionDao pRepo = new PositionDao(c);
//      QuoteHttpHelper rcon = new QuoteHttpHelper(properties.get("api-key"), client);
//      QuoteService sQuote = new QuoteService(qRepo, rcon);
//      PositionService sPos = new PositionService(pRepo);
//      StockQuoteController con = new StockQuoteController(sQuote, sPos);
//      con.initClient();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }
//
//}
