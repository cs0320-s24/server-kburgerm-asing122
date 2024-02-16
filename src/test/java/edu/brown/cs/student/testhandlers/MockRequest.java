package edu.brown.cs.student.testhandlers;

import spark.Request;

public class MockRequest extends Request {
  private String filePath;
  private String hasHeader;

  public MockRequest(String filePath, String hasHeader) {
    this.filePath = filePath;
    this.hasHeader = hasHeader;
  }

  public MockRequest() { }

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
}