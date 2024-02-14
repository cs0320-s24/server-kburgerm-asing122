package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.parser.LoadCSV;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {

    String filePath = request.queryParams("filePath");
    String hasHeader = request.queryParams("hasHeader");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      List<List<String>> CSVjson = this.sendRequest(filePath, Boolean.parseBoolean(hasHeader));
      responseMap.put("result", "success");
      responseMap.put("loadCSV", CSVjson);
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
      responseMap.put("result", "exception");
    }
    return responseMap;
  }

  private List<List<String>> sendRequest(String filepath, boolean hasHeader) {
    List<List<String>> csvJson = new LoadCSV(filepath, hasHeader).loadCSV();
    return csvJson;
  }
}
