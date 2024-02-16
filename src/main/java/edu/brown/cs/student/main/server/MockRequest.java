package edu.brown.cs.student.main.server;

import spark.Request;

public class MockRequest extends Request {
  private final String filePath;
  private final String hasHeader;

  public MockRequest(String filePath, String hasHeader) {
    this.filePath = filePath;
    this.hasHeader = hasHeader;
  }

  @Override
  public String queryParams(String name) {
    if (name.equals("filepath")) {
      return filePath;
    } else if (name.equals("hasHeader")) {
      return hasHeader;
    } else {
      return null;
    }
  }

  // Implement other methods if needed
}