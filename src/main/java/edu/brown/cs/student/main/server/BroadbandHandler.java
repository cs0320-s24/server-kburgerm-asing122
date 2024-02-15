package edu.brown.cs.student.main.server;

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

public class BroadbandHandler implements Route {

  private HashMap<String, String> stateCodes;
  private HashMap<String, Integer> countyCodes;

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

    // Send that API request then store the response in this variable. Note the generic type.
    HttpResponse<String> sentAcsApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildAcsApiRequest, HttpResponse.BodyHandlers.ofString());
    String jsonMap = sentAcsApiResponse.body();
    try {
      Moshi moshi = new Moshi.Builder().build();
      // System.out.println(jsonMap);
      Type mapType = Types.newParameterizedType(List.class, List.class, String.class);
      JsonAdapter<List<List<String>>> adapter = moshi.adapter(mapType);
      List<List<String>> deserializedStateMap = adapter.fromJson(jsonMap);
      // System.out.println(deserializedStateMap);
      return deserializedStateMap;
    } catch (IOException e) {
      throw e;
    }
  }

  private List<String[]> getStateCodes()
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest buildAcsApiRequest =
        HttpRequest.newBuilder()
            .uri(new URI("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*"))
            .GET()
            .build();

    // Send that API request then store the response in this variable. Note the generic type.
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

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String state = request.queryParams("state");
    String county = request.queryParams("county");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      String CSVjson = this.sendRequest(state, county);
      responseMap.put("result", "success");
      responseMap.put("broadband", CSVjson);
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
      responseMap.put("result", "exception");
    }
    return responseMap;
  }

  private String sendRequest(String state, String county)
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
      System.out.println("County not found");
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

    // Send that API request then store the response in this variable. Note the generic type.
    HttpResponse<String> sentAcsApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildAcsApiRequest, HttpResponse.BodyHandlers.ofString());

    System.out.println(sentAcsApiResponse.body());
    return sentAcsApiResponse.body();
  }
}
