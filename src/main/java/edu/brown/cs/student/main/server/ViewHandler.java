package edu.brown.cs.student.main.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A handler for viewing loaded CSV data.
 */
public class ViewHandler implements Route {

  List<List<String>> loadedFile;

  /**
   * Constructs a ViewHandler object with the given loaded CSV data.
   *
   * @param loadedFile The loaded CSV data.
   */
  public ViewHandler(List<List<String>> loadedFile) {
    this.loadedFile = loadedFile;
  }

  /**
   * Handles HTTP requests for viewing loaded CSV data.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @return A map containing the loaded CSV data.
   * @throws Exception If an error occurs during request handling.
   */
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
