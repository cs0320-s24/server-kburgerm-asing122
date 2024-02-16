package edu.brown.cs.student.main.server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

public class TestLoadHandler {

  private LoadHandler loadHandler;

  @BeforeEach
  public void setUp() {
    loadHandler = new LoadHandler();
  }

  @AfterEach
  public void tearDown() {
    // Clean up any resources if needed
  }

  @Test
  public void testLoadValidCSV() {
    String filePath = "data/census/income_by_race.csv";
    String hasHeader = "true";

    Request request = new MockRequest(filePath, hasHeader);
    Response response = new MockResponse();

    try {
      Object result = loadHandler.handle(request, response);

      assertNotNull(result);
      assertTrue(result instanceof Map);
      Map<String, Object> resultMap = (Map<String, Object>) result;
      assertEquals("success", resultMap.get("result"));
      assertEquals(filePath, resultMap.get("loadCSV"));
      assertNotNull(loadHandler.loadedFile);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  public void testLoadNonExistentCSVFile() {
    String filePath = "data/census/income_by_county.csv";
    String hasHeader = "true";

    Request request = new MockRequest(filePath, hasHeader);
    MockResponse response = new MockResponse();

    try {
      Object result = loadHandler.handle(request, response);

      assertNotNull(result);
      assertTrue(result instanceof Map);
      Map<String, Object> resultMap = (Map<String, Object>) result;
      assertEquals("exception", resultMap.get("result"));

    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  public void testLoadValidCSVWithoutHeader() {
    String filePath = "data/census/income_by_county.csv";
    String hasHeader = "false";

    Request request = new MockRequest(filePath, hasHeader);
    MockResponse response = new MockResponse();

    try {
      Object result = loadHandler.handle(request, response);

      assertNotNull(result);
      assertTrue(result instanceof Map);
      Map<String, Object> resultMap = (Map<String, Object>) result;
      assertEquals("exception", resultMap.get("result"));
      assertNull(resultMap.get("loadCSV"));
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }
}
