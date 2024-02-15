package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.parser.CSVParser;
import edu.brown.cs.student.main.searcher.Util;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadHandler implements Route {

  List<List<String>> loadedFile;

  @Override
  public Object handle(Request request, Response response) throws Exception {

    String filePath = request.queryParams("filePath");
    String hasHeader = request.queryParams("hasHeader");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      CSVParser parser =
          new CSVParser(new FileReader(filePath), new Util(), Boolean.parseBoolean(hasHeader));

      responseMap.put("result", "success");
      responseMap.put("loadCSV", filePath);

    } catch (Exception e) {
      responseMap.put("result", "exception");
    }
    return responseMap;
  }

  private String sendRequest(String filepath, boolean hasHeader)
      throws URISyntaxException, IOException, InterruptedException {
    return "";
  }

  record LoadSuccess(String result, String filepath) {
    public LoadSuccess(String filepath) {
      this("success", filepath);
    }
  }
}
