package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.broadband.BroadbandHandler;
import edu.brown.cs.student.main.server.broadband.CacheProxy;
import edu.brown.cs.student.main.server.broadband.strategy.BasicCacheConfig;
import spark.Spark;

/** The main class for starting the server. */
public class Server {

  /**
   * The main method to start the server.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    int port = 3230;
    Spark.port(port);

    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });
    CSVFile csvFile = new CSVFile();
    LoadHandler loadHandler = new LoadHandler(csvFile);
    Spark.get("loadcsv", loadHandler);
    Spark.get("broadband", new CacheProxy(new BroadbandHandler(), new BasicCacheConfig()));
    Spark.get("viewcsv", new ViewHandler(csvFile));
    Spark.get("searchcsv", new SearchHandler(csvFile));
    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
