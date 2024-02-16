package edu.brown.cs.student.testhandlers;

import static org.junit.jupiter.api.Assertions.*;

import edu.brown.cs.student.main.server.LoadHandler;
import edu.brown.cs.student.main.server.SearchHandler;
import edu.brown.cs.student.main.server.broadband.BroadbandHandler;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Spark;

public class TestLoadHandler {

  private LoadHandler loadHandler;

  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  @BeforeEach
  public void setup() {
    loadHandler = new LoadHandler();
    Spark.get("loadcsv", loadHandler);
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    Spark.unmap("loadcsv");
    // Spark.unmap("viewcsv");
    Spark.unmap("searchcsv");
    Spark.unmap("activity");
    Spark.awaitStop();
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
