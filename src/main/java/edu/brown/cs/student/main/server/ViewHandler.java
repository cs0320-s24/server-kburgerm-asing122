package edu.brown.cs.student.main.server;

import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

public class ViewHandler implements Route {

  List<List<String>> loadedFile;

  public ViewHandler(List<List<String>> loadedFile) {
    this.loadedFile = loadedFile;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}
