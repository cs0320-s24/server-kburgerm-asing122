package edu.brown.cs.student.main.parser;

import edu.brown.cs.student.main.parser.strategy.CreatorFromRow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class parses CSV data and organizes parsed data into objects of type T
 *
 * @param <T> - type which to return upon parsing file
 */
public class CSVParser<T> {
  private BufferedReader reader;
  private CreatorFromRow<T> creator;
  private boolean hasHeader;
  private T header;
  private final String regEx = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))";

  /**
   * Constructor for CSVParser.
   *
   * @param reader - Reader object containing the raw filepath or file data
   * @param creator - CreatorFromRow object that determines how and what objects to create from data
   * @param header - whether the file/data contains a header row
   */
  public CSVParser(Reader reader, CreatorFromRow<T> creator, boolean header) {

    BufferedReader in = new BufferedReader(reader);
    this.reader = in;
    this.creator = creator;
    this.hasHeader = header;
  }

  /**
   * Parses the reader passed to the parser's constructor into a list of the desired type.
   *
   * @return List(T) - list of objects to return of type T determined by creator field
   */
  public List<T> parse() throws IOException, FactoryFailureException {
    List<T> list = new ArrayList<T>();
    String s = this.reader.readLine();
    List<String> sList = new ArrayList<String>(Arrays.asList(s.split(this.regEx)));
    int stdLen = sList.size();
    if (this.hasHeader) {
      this.header = this.creator.create(sList);
    } else {
      list.add(this.creator.create(sList));
    }
    s = this.reader.readLine();
    while (s != null) {
      List<String> list1 = new ArrayList<>(Arrays.asList(s.split(this.regEx)));
      if (list1.size() == stdLen) {
        list.add(this.creator.create(list1));
      } else {
        throw new IOException();
      }
      s = this.reader.readLine();
    }
    return list;
  }

  /**
   * Returns the header object of the parsed CSV file. If the file does not contain a header, the
   * first row
   *
   * @return the header or first row
   */
  public T getHeader() {
    return this.header;
  }
}
