package edu.brown.cs.student.testhandlers;

import static org.testng.AssertJUnit.*;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.server.CSVFile;
import edu.brown.cs.student.main.server.LoadHandler;
import java.lang.reflect.Type;
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
  private CSVFile csvFile;

  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  @BeforeEach
  public void setup() {
    csvFile = new CSVFile();
    loadHandler = new LoadHandler(csvFile);
    Spark.get("loadcsv", loadHandler);
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    Spark.unmap("loadcsv");
    Spark.awaitStop();
  }

  @Test
  public void testLoadValidCSV() {
    String filePath = "data/census/income_by_race.csv";
    String hasHeader = "true";

    Request request = new MockRequest(filePath, hasHeader);
    Response response = new MockResponse();
    Moshi moshi = new Moshi.Builder().build();
    Type mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapType);
    try {
      Object result = loadHandler.handle(request, response);
      Map<String, Object> resultMap = adapter.fromJson(result.toString());
      assertNotNull(result);
      assertTrue(result instanceof String);

      assertEquals("success", resultMap.get("result"));
      assertEquals(filePath, resultMap.get("loadCSV"));
      assertNotNull(loadHandler.loadedFile);
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  @Test
  public void testLoadRICSV() {
    String filePath = "data/RI_income.csv";
    String hasHeader = "true";

    Request request = new MockRequest(filePath, hasHeader);
    Response response = new MockResponse();
    Moshi moshi = new Moshi.Builder().build();
    Type mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapType);
    try {
      Object result = loadHandler.handle(request, response);
      Map<String, Object> resultMap = adapter.fromJson(result.toString());
      assertNotNull(result);
      assertTrue(result instanceof String);
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
    Moshi moshi = new Moshi.Builder().build();
    Type mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapType);

    try {
      Object result = loadHandler.handle(request, response);
      Map<String, Object> resultMap = adapter.fromJson(result.toString());
      assertNotNull(result);
      assertTrue(result instanceof String);
      assertEquals("error_datasource", resultMap.get("result"));

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
    Moshi moshi = new Moshi.Builder().build();
    Type mapType = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapType);

    try {
      Object result = loadHandler.handle(request, response);
      Map<String, Object> resultMap = adapter.fromJson(result.toString());

      assertNotNull(result);
      assertTrue(result instanceof String);
      assertEquals("error_datasource", resultMap.get("result"));
      assertNull(resultMap.get("loadCSV"));
    } catch (Exception e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }
}
