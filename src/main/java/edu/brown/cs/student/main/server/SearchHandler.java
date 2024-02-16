package edu.brown.cs.student.main.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchHandler implements Route {

  List<List<String>> loadedFile;

  public SearchHandler(List<List<String>> loadedFile) {
    this.loadedFile = loadedFile;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    String target = request.queryParams("target");
    Boolean hasHeader = Boolean.parseBoolean(request.queryParams("hasHeader"));
    String column = request.queryParams("column");
    Map<String, Object> responseMap = new HashMap<>();
    List<List<String>> searchResults = new ArrayList<>();
    if (column.length() < 1) {
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
          searchResults = this.search(target, column);
          responseMap.put("result", "success");
          responseMap.put("searchResults", searchResults);
        }
      }
    }

    return responseMap;
  }

  private List<List<String>> search(String target, int colID) {
    List<List<String>> searchResults = new ArrayList<>();
    for (List<String> row : this.loadedFile) {
      if (row.get(colID).contains(target)) {
        searchResults.add(row);
      }
    }
    return searchResults;
  }

  private List<List<String>> search(String target, String colID) {
    List<List<String>> searchResults = new ArrayList<>();
    int col = 0;
    for (String s : this.loadedFile.get(0)) {
      if (s.equals(colID)) {
        break;
      }
      col++;
    }
    for (List<String> row : this.loadedFile) {
      if (row.get(col).contains(target)) {
        searchResults.add(row);
      }
    }
    return searchResults;
  }

  private List<List<String>> search(String target) {
    List<List<String>> searchResults = new ArrayList<>();
    for (List<String> row : this.loadedFile) {
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
