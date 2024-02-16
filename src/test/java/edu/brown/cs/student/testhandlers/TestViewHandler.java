package edu.brown.cs.student.testhandlers;

import static org.testng.AssertJUnit.*;

import edu.brown.cs.student.main.server.CSVFile;
import edu.brown.cs.student.main.server.LoadHandler;
import edu.brown.cs.student.main.server.ViewHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

public class TestViewHandler {

  private ViewHandler viewHandler;
  private LoadHandler loadHandler;

  @BeforeEach
  public void setUp() {
    List<List<String>> loadedFile = new ArrayList<>();
    List<String> row1 = List.of("1", "John", "Doe");
    List<String> row2 = List.of("2", "Jane", "Smith");
    loadedFile.add(row1);
    loadedFile.add(row2);
    CSVFile csvFile = new CSVFile();
    csvFile.currentCSV = loadedFile;
    viewHandler = new ViewHandler(csvFile);
  }

  @AfterEach
  public void tearDown() {}

  @Test
  public void testHandle() {
    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    try {
      Object result = viewHandler.handle(request, response);

      assertNotNull(result);
      assertTrue(result instanceof Map);
      Map<String, Object> resultMap = (Map<String, Object>) result;
      assertEquals("success", resultMap.get("result"));
      assertNotNull(resultMap.get("data"));
      assertTrue(resultMap.get("data") instanceof List);

    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  public void testHandleWithoutData() {
    viewHandler = new ViewHandler(null);

    Request request = new MockRequest();
    MockResponse response = new MockResponse();

    try {
      Object result = viewHandler.handle(request, response);

      assertNotNull(result);
      assertTrue(result instanceof Map);
      Map<String, Object> resultMap = (Map<String, Object>) result;
      assertEquals("success", resultMap.get("result"));
      assertNull(resultMap.get("data"));

    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  public void testViewAndLoad() {

    String filePath = "data/RI_income.csv";
    String hasHeader = "true";

    CSVFile csvFile = new CSVFile();
    loadHandler = new LoadHandler(csvFile);

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

    viewHandler = new ViewHandler(loadHandler.loadedFile);

    Request viewRequest = new MockRequest();
    MockResponse viewResponse = new MockResponse();

    try {
      Object result = viewHandler.handle(viewRequest, viewResponse);

      assertNotNull(result);
      assertTrue(result instanceof Map);
      Map<String, Object> resultMap = (Map<String, Object>) result;
      assertEquals("success", resultMap.get("result"));
      assertNull(resultMap.get("data"));

    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }
}
