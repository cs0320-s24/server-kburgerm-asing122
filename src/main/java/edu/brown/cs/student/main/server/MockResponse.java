package edu.brown.cs.student.main.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import spark.Response;

public class MockResponse extends Response {
  private final Map<String, String> headers = new HashMap<>();
  private final StringWriter writer = new StringWriter();

  @Override
  public void status(int statusCode) {
    // Do nothing for now
  }

  @Override
  public void header(String name, String value) {
    headers.put(name, value);
  }

  // Additional methods to capture output and headers if needed
  public String getOutput() {
    return writer.toString();
  }

  public Map<String, String> getHeaders() {
    return headers;
  }
}

