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
import spark.Spark;

/**
 * A handler for loading CSV files.
 */
public class LoadHandler implements Route {

  public List<List<String>> loadedFile;

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
      this.loadedFile = parser.parse();
    } catch (Exception e) {
      responseMap.put("result", "error_datasource");
    }
    return responseMap;
  }

  /**
   * Sends a request to load a CSV file.
   *
   * @param filepath The path to the CSV file.
   * @param hasHeader Indicates whether the CSV file has a header.
   * @return A string representing the result of the request.
   * @throws URISyntaxException If the URI is invalid.
   * @throws IOException If an I/O error occurs.
   * @throws InterruptedException If the operation is interrupted.
   */
  private String sendRequest(String filepath, boolean hasHeader)
      throws URISyntaxException, IOException, InterruptedException {
    return "";
  }

  /**
   * Represents the result of a successful CSV file load.
   */
  record LoadSuccess(String result, String filepath) {
    /**
     * Constructs a LoadSuccess object with the specified file path.
     *
     * @param filepath The file path of the loaded CSV file.
     */
    public LoadSuccess(String filepath) {
      this("success", filepath);
    }
  }
}
