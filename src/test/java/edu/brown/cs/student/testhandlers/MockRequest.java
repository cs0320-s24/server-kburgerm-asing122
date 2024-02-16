package edu.brown.cs.student.testhandlers;

import spark.Request;

public class MockRequest extends Request {
  private boolean intColumn = false;
  private String filePath;
  private String hasHeader;
  private String target;
  private String column;
  private int columnIndex;

  public MockRequest(String filePath, String hasHeader) {
    this.filePath = filePath;
    this.hasHeader = hasHeader;
  }

  public MockRequest(String target, boolean hasHeader, String column) {
    this.target = target;
    this.hasHeader = Boolean.toString(hasHeader);
    this.column = column;
  }

  public MockRequest(String target, boolean hasHeader, int column) {
    this.target = target;
    this.hasHeader = Boolean.toString(hasHeader);
    this.columnIndex = column;
    this.intColumn = true;
  }

  public MockRequest(String target, boolean hasHeader) {
    this.target = target;
    this.hasHeader = Boolean.toString(hasHeader);
  }

  public MockRequest() {}

  @Override
  public String queryParams(String name) {
    if (name.equals("filepath")) {
      return filePath;
    } else if (name.equals("hasHeader")) {
      return hasHeader;
    } else if (name.equals("target")) {
      return target;
    } else if (name.equals("column")) {
      if (intColumn) {
        return Integer.toString(columnIndex);
      } else {
        return column;
      }
    } else {
      return "";
    }
  }
}
