package edu.brown.cs.student.main.server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {

    Set<String> params = request.queryParams();
    String filePath = request.queryParams("filePath");
    String hasHeader = request.queryParams("hasHeader");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      String CSVjson = this.sendRequest(filePath, Boolean.parseBoolean(hasHeader));
      responseMap.put("result", "success");
      responseMap.put("loadCSV", CSVjson);
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
      responseMap.put("result", "exception");
    }
    return responseMap;
  }

  private String sendRequest(String filepath, boolean hasHeader)
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest buildAcsApiRequest =
        HttpRequest.newBuilder()
            .uri(
                new URI(
                    "https://api.census.gov/data/2021/acs/acs1/subject/variables?get=NAME,S2802_C03_022E&for=county:*&in=state:06"))
            .GET()
            .build();

    // Send that API request then store the response in this variable. Note the generic type.
    HttpResponse<String> sentAcsApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildAcsApiRequest, HttpResponse.BodyHandlers.ofString());

    System.out.println(sentAcsApiResponse.body());
    return sentAcsApiResponse.body();
  }
}
