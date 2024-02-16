package edu.brown.cs.student.testhandlers;

import static org.testng.AssertJUnit.*;

import edu.brown.cs.student.main.server.broadband.BroadbandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class TestBroadbandHandler {

  private BroadbandHandler broadbandHandler;

  @BeforeEach
  public void setUp() {
    broadbandHandler = new BroadbandHandler();
  }

  @Test
  public void testHandleSuccess() throws Exception {
    Request request = createMockRequest("California", "Los Angeles");
    Response response = createMockResponse();

    Object result = broadbandHandler.handle(request, response);

    Map<String, Object> resultMap = (Map<String, Object>) result;
    assertEquals("success", resultMap.get("result"));
    assertEquals("California", resultMap.get("state"));
    assertEquals("Los Angeles", resultMap.get("county"));
  }

  @Test
  public void testHandleException() throws Exception {
    Request request = createMockRequest("InvalidState", "InvalidCounty");
    Response response = createMockResponse();

    Object result = broadbandHandler.handle(request, response);

    Map<String, Object> resultMap = (Map<String, Object>) result;
    assertEquals("exception", resultMap.get("result"));
  }

  @Test
  public void testSendRequest() throws Exception {
    String mockResponse = "[[\"NAME\",\"S2802_C03_022E\",\"state\",\"county\"],\n"
        + "[\"Los Angeles County, California\",\"89.9\",\"06\",\"037\"]]";

    String result = broadbandHandler.sendRequest("California", "Los Angeles County");

    assertEquals(mockResponse, result);
  }

  @Test
  public void testSendRequestCountyNotFound() throws Exception {
    // Test when county is not found
  }

  @Test
  public void testSendRequestIOException() throws Exception {
    // broadbandHandler.sendRequest("InvalidState", "InvalidCounty");
  }

  @Test
  public void testSendRequestURISyntaxException() throws Exception {
    // broadbandHandler.sendRequest("Invalid:State", "Invalid:County");
  }

  private Request createMockRequest(String state, String county) {
    return new Request() {
      @Override
      public String queryParams(String param) {
        if (param.equals("state"))
          return state;
        else if (param.equals("county"))
          return county;
        return null;
      }
    };
  }

  // Helper method to create a mock Response
  private Response createMockResponse() {
    return new Response() {
      @Override
      public void status(int statusCode) {}
    };
  }
}
