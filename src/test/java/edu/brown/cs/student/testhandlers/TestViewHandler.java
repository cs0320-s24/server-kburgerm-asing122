package edu.brown.cs.student.testhandlers;

import static org.junit.jupiter.api.Assertions.*;

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

  @BeforeEach
  public void setUp() {
    List<List<String>> loadedFile = new ArrayList<>();
    List<String> row1 = List.of("1", "John", "Doe");
    List<String> row2 = List.of("2", "Jane", "Smith");
    loadedFile.add(row1);
    loadedFile.add(row2);

    viewHandler = new ViewHandler(loadedFile);
  }

  @AfterEach
  public void tearDown() {

  }

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
}