package edu.brown.cs.student.testhandlers;

import static org.testng.AssertJUnit.*;

import edu.brown.cs.student.main.server.CSVFile;
import edu.brown.cs.student.main.server.SearchHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.units.qual.C;
import org.testng.annotations.Test;
import spark.Request;
import spark.Response;

public class TestSearchHandler {

  @Test
  public void testSearchInSpecificColumn() throws Exception {
    List<List<String>> testData =
        Arrays.asList(
            Arrays.asList("apple", "banana", "orange"),
            Arrays.asList("cat", "dog", "elephant"),
            Arrays.asList("apple pie", "banana bread", "orange juice"));
    CSVFile csvFile = new CSVFile();
    csvFile.currentCSV = testData;
    SearchHandler searchHandler = new SearchHandler(csvFile);
    MockRequest request = new MockRequest("apple", false, "1");
    Response response = new MockResponse();
    Map<String, Object> result = (Map<String, Object>) searchHandler.handle(request, response);

    List<List<String>> expectedResults =
        Arrays.asList(
            Arrays.asList("apple", "banana", "orange"),
            Arrays.asList("apple pie", "banana bread", "orange juice"));

    assertEquals(expectedResults, result.get("searchResults"));
  }

  @Test
  public void testSearchWithColumnName() throws Exception {
    List<List<String>> testData =
        Arrays.asList(
            Arrays.asList("Name", "Age", "Country"),
            Arrays.asList("John", "25", "USA"),
            Arrays.asList("Alice", "30", "Canada"));
    CSVFile csvFile = new CSVFile();
    csvFile.currentCSV = testData;
    SearchHandler searchHandler = new SearchHandler(csvFile);
    Request request = new MockRequest();
    Response response = new MockResponse();
    Map<String, Object> result = (Map<String, Object>) searchHandler.handle(request, response);

    List<List<String>> expectedResults =
        Arrays.asList(Arrays.asList("John", "25", "USA"), Arrays.asList("Alice", "30", "Canada"));

    assertEquals(result.get("searchResults"), expectedResults);
  }

  @Test
  public void testSearchAcrossAllColumns() throws Exception {
    List<List<String>> testData =
        Arrays.asList(
            Arrays.asList("apple", "banana", "orange"),
            Arrays.asList("cat", "dog", "elephant"),
            Arrays.asList("apple pie", "banana bread", "orange juice"));
    CSVFile csvFile = new CSVFile();
    csvFile.currentCSV = testData;
    SearchHandler searchHandler = new SearchHandler(csvFile);
    Request request = new MockRequest();
    Response response = new MockResponse();
    Map<String, Object> result = (Map<String, Object>) searchHandler.handle(request, response);

    List<List<String>> expectedResults =
        Arrays.asList(
            Arrays.asList("apple", "banana", "orange"),
            Arrays.asList("cat", "dog", "elephant"),
            Arrays.asList("apple pie", "banana bread", "orange juice"));

    assertEquals(result.get("searchResults"), expectedResults);
  }
}
