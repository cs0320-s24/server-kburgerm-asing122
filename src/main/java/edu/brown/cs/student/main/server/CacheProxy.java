package edu.brown.cs.student.main.server;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import edu.brown.cs.student.main.server.BroadbandHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import spark.Request;
import spark.Response;
import spark.Route;

public class CacheProxy implements Route {
  private final BroadbandHandler broadbandHandler;
  private final LoadingCache<String, String> cache;

  public CacheProxy(BroadbandHandler broadbandHandler, int minutes, int size) {
    this.broadbandHandler = broadbandHandler;
    this.cache = CacheBuilder.newBuilder()
        .maximumSize(size)
        .expireAfterWrite(minutes, TimeUnit.MINUTES)
        .recordStats()
        .build(new CacheLoader<String, String>() {
          @Override
          public String load(String key) throws Exception {
            String[] params = key.split(",");
            String state = params[0];
            String county = params[1];
            return broadbandHandler.sendRequest(state, county);
          }
        });
  }

  public String getCachedData(String state, String county) {
    try {
      return cache.get(state + "," + county);
    } catch (Exception e) {
      System.out.println("Error fetching data from cache: " + e.getMessage());
      return "";
    }
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String state = request.queryParams("state");
    String county = request.queryParams("county");

    Map<String, Object> responseMap = new HashMap<>();
    try {
      String cachedResponse = cache.get(state + ":" + county);
      responseMap.put("result", "success");
      responseMap.put("state", state);
      responseMap.put("county", county);
      responseMap.put("broadband", cachedResponse);
    } catch (ExecutionException e) {
      responseMap.put("result", "exception");
    }
    return responseMap;
  }
}
