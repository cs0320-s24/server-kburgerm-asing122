package edu.brown.cs.student.main.server.broadband;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import edu.brown.cs.student.main.server.broadband.strategy.CacheConfig;
import spark.Request;
import spark.Response;
import spark.Route;

public class CacheProxy implements Route {
  private final BroadbandHandler broadbandHandler;
  private final LoadingCache<String, String> cache;

  public CacheProxy(BroadbandHandler broadbandHandler, CacheConfig config) {
    this.broadbandHandler = broadbandHandler;
    this.cache =
        config
            .configureCache()
            .build(
                new CacheLoader<String, String>() {
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
    return broadbandHandler.handle(request, response);
  }
}
