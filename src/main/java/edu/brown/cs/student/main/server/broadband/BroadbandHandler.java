package edu.brown.cs.student.main.server.broadband;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/** Handles HTTP requests related to broadband information. */
public class BroadbandHandler implements Route {

  private HashMap<String, String> stateCodes;
  private HashMap<String, Integer> countyCodes;

  /** Initializes the BroadbandHandler and loads state codes. */
  public BroadbandHandler() {
    try {
      this.stateCodes = new HashMap<String, String>();
      List<String[]> stateCodeList = this.getStateCodes();
      stateCodeList.remove(0);
      for (String[] stateAndCode : stateCodeList) {
        this.stateCodes.put(stateAndCode[0], stateAndCode[1]);
      }
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieves the county codes for a given state.
   *
   * @param stateCode The code of the state.
   * @return A list of county codes.
   * @throws IOException If an I/O error occurs.
   * @throws InterruptedException If the operation is interrupted.
   * @throws URISyntaxException If the URI syntax is incorrect.
   */
  private List<List<String>> getCountyCodes(String stateCode)
      throws IOException, InterruptedException, URISyntaxException {
    HttpRequest buildAcsApiRequest =
        HttpRequest.newBuilder()
            .uri(
                new URI(
                    "https://api.census.gov/data/2010/dec/sf1?get=NAME&in=state:"
                        + stateCode
                        + "&for=county:*"))
            .GET()
            .build();

    HttpResponse<String> sentAcsApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildAcsApiRequest, HttpResponse.BodyHandlers.ofString());
    String jsonMap = sentAcsApiResponse.body();
    try {
      Moshi moshi = new Moshi.Builder().build();
      Type mapType = Types.newParameterizedType(List.class, List.class, String.class);
      JsonAdapter<List<List<String>>> adapter = moshi.adapter(mapType);
      List<List<String>> deserializedStateMap = adapter.fromJson(jsonMap);
      return deserializedStateMap;
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Retrieves the state codes.
   *
   * @return A list of state codes.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws IOException If an I/O error occurs.
   * @throws InterruptedException If the operation is interrupted.
   */
  private List<String[]> getStateCodes()
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest buildAcsApiRequest =
        HttpRequest.newBuilder()
            .uri(new URI("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*"))
            .GET()
            .build();

    HttpResponse<String> sentAcsApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildAcsApiRequest, HttpResponse.BodyHandlers.ofString());
    String jsonMap = sentAcsApiResponse.body();
    try {
      Moshi moshi = new Moshi.Builder().build();
      Type mapType = Types.newParameterizedType(List.class, String[].class);
      JsonAdapter<List<String[]>> adapter = moshi.adapter(mapType);
      List<String[]> deserializedStateMap = adapter.fromJson(jsonMap);
      return deserializedStateMap;
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Handles HTTP requests.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @return A map containing the response data.
   * @throws Exception If an error occurs during request handling.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String state = request.queryParams("state");
    String county = request.queryParams("county");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      String CSVjson = this.sendRequest(state, county);
      if (CSVjson.equals("")) {
        responseMap.put("result", "error_datasource");
        return responseMap;
      }
      responseMap.put("broadband", CSVjson);

      responseMap.put("result", "success");
      responseMap.put("state", state);
      responseMap.put("county", county);
    } catch (Exception e) {
      responseMap.put("result", "error_bad_request");
    }
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map> jsonAdapter = moshi.adapter(Map.class);
    String jsonString = jsonAdapter.toJson(responseMap);
    return jsonString;
  }

  /**
   * Sends a request to fetch broadband information.
   *
   * @param state The state for which information is requested.
   * @param county The county for which information is requested.
   * @return The broadband information in JSON format.
   * @throws URISyntaxException If the URI is invalid.
   * @throws IOException If an I/O error occurs.
   * @throws InterruptedException If the operation is interrupted.
   */
  public String sendRequest(String state, String county)
      throws URISyntaxException, IOException, InterruptedException {
    String stateCode = this.stateCodes.get(state);
    List<List<String>> countyCodes = this.getCountyCodes(stateCode);
    String countyCode = "";
    for (List<String> countyInfo : countyCodes) {
      String countyName = countyInfo.get(0).split(",")[0];
      if (countyName.equals(county)) {
        countyCode = countyInfo.get(2);
      }
    }
    if (countyCode.equals("")) {
      return "";
    }
    HttpRequest buildAcsApiRequest =
        HttpRequest.newBuilder()
            .uri(
                new URI(
                    "https://api.census.gov/data/2021/acs/acs1/subject/"
                        + "variables?get=NAME,S2802_C03_022E&for=county:"
                        + countyCode
                        + "&in=state:"
                        + stateCode))
            .GET()
            .build();

    HttpResponse<String> sentAcsApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildAcsApiRequest, HttpResponse.BodyHandlers.ofString());
    return sentAcsApiResponse.body();
  }
}
