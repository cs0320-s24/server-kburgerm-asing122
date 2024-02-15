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

    Spark.get("loadcsv", new LoadHandler());
    Spark.get("broadband", new BroadbandHandler());

    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
