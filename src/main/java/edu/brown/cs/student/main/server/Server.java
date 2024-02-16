package edu.brown.cs.student.main.server;

import static spark.Spark.after;

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
    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
