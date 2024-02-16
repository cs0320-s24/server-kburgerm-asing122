package edu.brown.cs.student.main.server.broadband;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import edu.brown.cs.student.main.server.broadband.strategy.CacheConfig;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * CacheProxy is a proxy class that handles caching of broadband data. It uses a LoadingCache to
 * cache the results of broadband queries based on state and county.
 */
public class CacheProxy implements Route {
  private final BroadbandHandler broadbandHandler;
  private final LoadingCache<String, String> cache;

  /**
   * Constructs a CacheProxy with the given BroadbandHandler and CacheConfig.
   *
   * @param broadbandHandler The BroadbandHandler instance to handle broadband requests.
   * @param config The CacheConfig instance to configure the cache.
   */
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

  /**
   * Retrieves cached broadband data for the given state and county.
   *
   * @param state The state for which to retrieve broadband data.
   * @param county The county for which to retrieve broadband data.
   * @return The cached broadband data as a String.
   */
  public String getCachedData(String state, String county) {
    try {
      return cache.get(state + "," + county);
    } catch (Exception e) {
      System.out.println("Error fetching data from cache: " + e.getMessage());
      return "";
    }
  }

  /**
   * Handles HTTP requests by delegating to the underlying BroadbandHandler.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @return The result of handling the request.
   * @throws Exception If an error occurs during request handling.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    return broadbandHandler.handle(request, response);
  }
}
