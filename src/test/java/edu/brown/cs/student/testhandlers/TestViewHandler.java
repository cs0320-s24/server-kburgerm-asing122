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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class TestViewHandler {
  private JsonAdapter<Map<String, Object>> adapter;

  public TestViewHandler() {
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
  public void testLoadCSV() throws IOException {
    HttpURLConnection clientConnection =
        tryRequest("loadcsv?filepath=data/census/income_by_race.csv&hasHeader=true");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
  }

  @Test
  public void testViewCSV() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/census/income_by_race.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("viewcsv");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
  }

  @Test
  public void testViewRIIncome() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/RI_income.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("viewcsv");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
  }

  @Test
  public void testViewNoCSV() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/census/RI_income.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("viewcsv");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("error_datasource", response.get("result"));
  }

  @Test
  public void testViewOtherCSV() throws IOException {
    HttpURLConnection clientConnection1 =
        tryRequest("loadcsv?filepath=data/census/income_by_race.csv&hasHeader=true");
    assertEquals(200, clientConnection1.getResponseCode());
    HttpURLConnection clientConnection = tryRequest("viewcsv");
    assertEquals(200, clientConnection.getResponseCode());
    Map<String, Object> response =
        adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
    assertEquals("success", response.get("result"));
  }
}
