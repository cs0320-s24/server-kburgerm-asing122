package edu.brown.cs.student.main.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileSearcher implements Searcher<String, String> {
  private final List<String> lines = new ArrayList<>();

  private void initialize(String filename) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    }
  }

  public FileSearcher(String filename) throws IOException {
    initialize(filename);
  }

  @Override
  public Collection<String> search(String target) {
    List<String> result = new ArrayList<>();
    for (String line : lines) {
      if (line.contains(target)) result.add(line);
    }
    return result;
  }
}
