package edu.brown.cs.student.main.parser;

import edu.brown.cs.student.main.searcher.Util;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LoadCSV {
  private String filePath;
  private boolean hasHeader;
  private boolean hasLoaded;

  public LoadCSV(String filePath, boolean hasHeader) {
    this.filePath = filePath;
    this.hasHeader = hasHeader;
  }

  public List<List<String>> loadCSV() {
    try {
      Util util = new Util();
      FileReader reader = new FileReader(filePath);
      CSVParser parser = new CSVParser<>(reader, util, hasHeader);
      List<List<String>> loadedCSV = parser.parse();
      this.hasLoaded = true;
      return loadedCSV;
    } catch (IOException | FactoryFailureException e) {
      this.hasLoaded = false;
      throw new RuntimeException(e);
    }
  }
}
