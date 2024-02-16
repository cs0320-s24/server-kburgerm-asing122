package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.parser.CSVParser;
import edu.brown.cs.student.main.parser.strategy.Util;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/** A handler for loading CSV files. */
public class LoadHandler implements Route {

  public CSVFile loadedFile;

  public LoadHandler(CSVFile csvFile) {
    this.loadedFile = csvFile;
  }

  /**
   * Handles HTTP requests to load a CSV file.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @return A map containing the response data.
   * @throws Exception If an error occurs during request handling.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {

    String filePath = request.queryParams("filepath");
    String hasHeader = request.queryParams("hasHeader");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      CSVParser parser =
          new CSVParser(new FileReader(filePath), new Util(), Boolean.parseBoolean(hasHeader));

      responseMap.put("result", "success");
      responseMap.put("loadCSV", filePath);
      this.loadedFile.setCurrentCSV(parser.parse());
    } catch (Exception e) {
      responseMap.put("result", "error_datasource");
    }
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map> jsonAdapter = moshi.adapter(Map.class);
    String jsonString = jsonAdapter.toJson(responseMap);
    return jsonString;
  }

}
