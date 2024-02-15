package edu.brown.cs.student.main.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Map<String, Object> responseMap = new HashMap<>();
    try {
      responseMap.put("result", "success");
      responseMap.put("data", this.loadedFile);
    } catch (Exception e) {
      responseMap.put("result", "failure");
    }
    return responseMap;
  }
}
