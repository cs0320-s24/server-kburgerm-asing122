package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

/** A handler for searching loaded CSV data. */
public class SearchHandler implements Route {

  CSVFile loadedFile;

  /**
   * Constructs a SearchHandler object with the given loaded CSV data.
   *
   * @param csvFile The loaded CSV data.
   */
  public SearchHandler(CSVFile csvFile) {
    this.loadedFile = csvFile;
  }

  /**
   * Handles HTTP requests for searching loaded CSV data.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @return A map containing the search results.
   * @throws Exception If an error occurs during request handling.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    Set<String> params = request.queryParams();

    Map<String, Object> responseMap = new HashMap<>();
    String target = request.queryParams("target");
    Boolean hasHeader = Boolean.parseBoolean(request.queryParams("hasHeader"));
    String column = request.queryParams("column");
    List<List<String>> searchResults = new ArrayList<>();
    if (!params.contains("target")) {
      responseMap.put("result", "error_bad_request");
    } else if (!params.contains("column")) {
      searchResults = this.search(target);
      responseMap.put("result", "success");
      responseMap.put("searchResults", searchResults);
    } else {
      try {
        int col = Integer.parseInt(column);
        searchResults = this.search(target, col);
        responseMap.put("result", "success");
        responseMap.put("searchResults", searchResults);
      } catch (NumberFormatException e) {
        if (hasHeader) {
          int col = this.getColIndex(column);
          if (col >= this.loadedFile.currentCSV.get(0).size() || col < 0) {
            responseMap.put("result", "error_bad_request");
          } else {
            searchResults = this.search(target, col);
            responseMap.put("result", "success");
            responseMap.put("searchResults", searchResults);
          }
        } else {
          responseMap.put("result", "error_bad_request");
        }
      }
    }
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map> jsonAdapter = moshi.adapter(Map.class);
    String jsonString = jsonAdapter.toJson(responseMap);
    return jsonString;
  }

  /**
   * Searches for a target string in a specific column of the loaded CSV data.
   *
   * @param target The target string to search for.
   * @param colID The column index to search in.
   * @return A list of search results.
   */
  private List<List<String>> search(String target, int colID) {
    List<List<String>> searchResults = new ArrayList<>();
    for (List<String> row : this.loadedFile.currentCSV) {
      if (row.get(colID).contains(target)) {
        searchResults.add(row);
      }
    }
    return searchResults;
  }

  /**
   * Returns the integer index representing the column of the given name
   *
   * @param column
   * @return index corresponding to coulmn name
   */
  private int getColIndex(String column) {
    int col = 0;
    for (String s : this.loadedFile.currentCSV.get(0)) {
      if (s.equals(column)) {
        break;
      }
      col++;
    }
    return col;
  }

  /**
   * Searches for a target string in a column specified by its name/header.
   *
   * @param target The target string to search for.
   * @param colID The name/header of the column to search in.
   * @return A list of search results.
   */
  private List<List<String>> search(String target, String colID) {
    List<List<String>> searchResults = new ArrayList<>();
    int col = 0;
    for (String s : this.loadedFile.currentCSV.get(0)) {
      if (s.equals(colID)) {
        break;
      }
      col++;
    }
    for (List<String> row : this.loadedFile.currentCSV) {
      if (row.get(col).contains(target)) {
        searchResults.add(row);
      }
    }
    return searchResults;
  }

  /**
   * Searches for a target string across all columns of the loaded CSV data.
   *
   * @param target The target string to search for.
   * @return A list of search results.
   */
  private List<List<String>> search(String target) {
    List<List<String>> searchResults = new ArrayList<>();
    for (List<String> row : this.loadedFile.currentCSV) {
      for (String s : row) {
        if (s.contains(target)) {
          searchResults.add(row);
          break;
        }
      }
    }
    return searchResults;
  }
}
