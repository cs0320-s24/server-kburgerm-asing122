package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.server.broadband.BroadbandHandler;
import edu.brown.cs.student.main.server.broadband.CacheProxy;
import edu.brown.cs.student.main.server.broadband.strategy.BasicCacheConfig;
import spark.Spark;

public class Server {

  public static void main(String[] args) {
    int port = 3232;
    Spark.port(port);

    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });
    LoadHandler loadHandler = new LoadHandler();
    Spark.get("loadcsv", loadHandler);
    Spark.get("broadband", new CacheProxy(new BroadbandHandler(), new BasicCacheConfig()));
    Spark.get(
        "viewcsv",
        (request, response) -> new ViewHandler(loadHandler.loadedFile).handle(request, response));
    Spark.get(
        "searchcsv",
        (request, response) -> new SearchHandler(loadHandler.loadedFile).handle(request, response));
    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
