package edu.brown.cs.student.testhandlers;

import static org.testng.AssertJUnit.assertEquals;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.server.CSVFile;
import edu.brown.cs.student.main.server.LoadHandler;
import edu.brown.cs.student.main.server.SearchHandler;
import edu.brown.cs.student.main.server.ViewHandler;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class TestSearchHandler {
  private JsonAdapter<Map<String, Object>> adapter;

  public TestSearchHandler() {
    Moshi moshi = new Moshi.Builder().build();
    Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
    adapter = moshi.adapter(type);
  }

  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
  }

  @BeforeEach
  public void setup() {
    // Re-initialize state, etc. for _every_ test method run
    CSVFile accessCSV = new CSVFile();
    // In fact, restart the entire Spark server for every test!
    Spark.get("loadcsv", new LoadHandler(accessCSV));
    Spark.get("searchcsv", new SearchHandler(accessCSV));
    Spark.get("viewcsv", new ViewHandler(accessCSV));
    Spark.init();
    Spark.awaitInitialization(); // don't continue until the server is listening
  }

  @AfterEach
  public void teardown() {
    // Gracefully stop Spark listening on both endpoints after each test
    Spark.unmap("loadcsv");
    Spark.unmap("searchcsv");
    Spark.unmap("viewcsv");
    Spark.awaitStop(); // don't proceed until the server is stopped
  }

  private static HttpURLConnection tryRequest(String apiCall) throws IOException {
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();

    clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }

  @Test
  public void testSearchNoColumns() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/RI_income.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("searchcsv?target=39,603.00");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
    assertEquals(Arrays.asList(Arrays.asList("Rhode Island", "74,489.00", "95,198.00", "39,603.00")),
        response.get("searchResults"));
  }

  @Test
  public void testSearchInvalid() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/RI_income.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("searchcsv?target=39,605.00");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
    assertEquals(Arrays.asList(),
        response.get("searchResults"));
  }

  @Test
  public void testSearchWithColumnIndex() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/RI_income.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("searchcsv?target=36,148.00&column=3");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
    assertEquals(Arrays.asList(Arrays.asList("West Warwick","62,649.00","80,699.00","36,148.00")),
        response.get("searchResults"));
  }

  @Test
  public void testSearchEmptyWithColumn() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/RI_income.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("searchcsv?target=36,148.00&column=1");
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
    assertEquals(Arrays.asList(),
        response.get("searchResults"));
  }

  @Test
  public void testSearchWithColumnName() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/RI_income.csv&hasHeader=false");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("searchcsv?target=36,148.00&hasHeader=true&column=Per%20Capita%20Income");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
    assertEquals(Arrays.asList(Arrays.asList("West Warwick","62,649.00","80,699.00","36,148.00")),
        response.get("searchResults"));
  }
}
