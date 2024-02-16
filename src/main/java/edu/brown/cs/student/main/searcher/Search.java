package edu.brown.cs.student.main.searcher;

import edu.brown.cs.student.main.parser.CSVParser;
import edu.brown.cs.student.main.parser.FactoryFailureException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to search a CSV file. Parses file, converts to a list of lists of strings, searches
 * based on user input
 */
public class Search {

  private CSVParser<List<String>> parser;
  private FileReader reader;
  private Util util;
  private List<List<String>> searchResults;

  /**
   * Constructor for Search. Searches a given file for the target query
   *
   * @param filename - name of file to parse and search
   * @param target - desired search term
   * @param header - whether the csv file has a header row
   * @param colID - name of header column to restrict search to
   */
  public Search(String filename, String target, boolean header, String colID) {
    try {
      this.reader = new FileReader(filename);
      this.util = new Util();
      this.parser = new CSVParser<>(this.reader, this.util, header);
      this.searchResults = new ArrayList<>();
    } catch (FileNotFoundException | NullPointerException e) {
      System.out.println(
          "There was a problem loading your file. Please make sure the path is correct and from a directory to which you have access.");
      System.exit(0);
    }
    this.search(target, colID);
  }

  public Search(String target, boolean header, String colID) {
    this.searchResults = new ArrayList<>();
    this.search(target, colID);
  }

  /**
   * Constructor for Search. Searches a given file for the target query
   *
   * @param filename - name of file to parse and search
   * @param target - desired search term
   * @param header - whether the csv file has a header row
   * @param colID - integer index of header column to restrict search to
   */
  public Search(String filename, String target, boolean header, int colID) {
    try {
      this.reader = new FileReader(filename);
      this.util = new Util();
      this.parser = new CSVParser<>(this.reader, this.util, header);
      this.searchResults = new ArrayList<>();
    } catch (FileNotFoundException | NullPointerException e) {
      System.out.println(
          "There was a problem loading your file. Please make sure the path is correct and from a directory to which you have access.");
      System.exit(0);
    }
    this.search(target, colID);
  }

  /**
   * Constructor for Search. Searches a given file for the target query
   *
   * @param filename - name of file to parse and search
   * @param target - desired search term
   * @param header - whether the csv file has a header row
   */
  public Search(String filename, String target, boolean header) {
    try {
      this.reader = new FileReader(filename);
      this.util = new Util();
      this.parser = new CSVParser<>(this.reader, this.util, header);
      this.searchResults = new ArrayList<>();
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Ensure your file path is correct and free of typos");
      System.exit(0);
    }
    this.search(target);
  }

  /**
   * Method that handles the actual search itself. Iterates through the generated List of strings
   * for rows matching the search query, and prints those that match to the terminal
   *
   * @param target - desired search query
   * @param colID - name of header column to search
   * @throws FactoryFailureException
   */
  private void search(String target, String colID) {
    List<List<String>> table = null;
    int col = 0;
    try {
      table = this.parser.parse();
      for (String s : this.parser.getHeader()) {
        if (s.equals(colID)) {
          break;
        }
        col++;
      }
      if (col >= this.parser.getHeader().size()) {
        System.out.println(
            "Search failed. Ensure that your desired column to search matches the header column exactly.");
        System.exit(1);
      }
    } catch (IOException e) {
      System.out.println(
          "Parse failed. Ensure that rows are of equal length and that the CSV does not contain malformed data.");
      System.exit(1);
    } catch (FactoryFailureException e) {
      System.out.println(
          "Parse failed. Ensure that rows are of equal length and that the CSV does not contain malformed data.");
      System.exit(1);
    }
    int count = 0;
    for (List<String> row : table) {
      if (row.get(col).equals(target)) {
        System.out.println(row);
        this.searchResults.add(row);
        count++;
      }
    }
    if (count == 0) {
      System.out.println(
          "Sorry, no rows matched your search. Keep in mind the search is case-sensitive and only returns exact matches"
              + " and ensure that your inputs are in the correct order.");
    }
  }

  /**
   * Method that handles the actual search itself. Iterates through the generated List of strings
   * for rows matching the search query, and prints those that match to the terminal
   *
   * @param target - desired search query
   * @param colID - integer index of header column to search
   * @throws FactoryFailureException
   */
  private void search(String target, int colID) {
    List<List<String>> table = null;
    try {
      table = this.parser.parse();
    } catch (IOException e) {
      System.out.println(
          "Parse failed. Ensure that rows are of equal length and that the CSV does not contain malformed data.");
      System.exit(1);
    } catch (FactoryFailureException e) {
      System.out.println(
          "Parse failed. Ensure that rows are of equal length and that the CSV does not contain malformed data.");
      System.exit(1);
    }
    int count = 0;
    for (List<String> row : table) {
      if (colID >= row.size() || colID < 0) {
        System.out.println(
            "Bad column input. Ensure your column is greater than 0 and less than the size of each row");
        System.exit(1);
      }
      if (row.get(colID).equals(target)) {
        System.out.println(row);
        this.searchResults.add(row);
        count++;
      }
    }
    if (count == 0) {
      System.out.println(
          "Sorry, no rows matched your search. Keep in mind the search is case-sensitive and only returns exact matches"
              + " and ensure that your inputs are in the correct order.");
    }
  }

  /**
   * Method that handles the actual search itself. Iterates through the generated List of strings
   * for rows matching the search query, and prints those that match to the terminal
   *
   * @param target - desired search query
   * @throws FactoryFailureException
   */
  private void search(String target) {
    List<List<String>> table = null;
    try {
      table = this.parser.parse();
    } catch (IOException e) {
      System.out.println(
          "Parse failed. Ensure that rows are of equal length and that the CSV does not contain malformed data.");
      System.exit(1);
    } catch (FactoryFailureException e) {
      System.out.println(
          "Parse failed. Ensure that rows are of equal length and that the CSV does not contain malformed data.");
      System.exit(1);
    }
    int count = 0;
    for (List<String> row : table) {
      for (String s : row) {
        if (s.equals(target)) {
          System.out.println(row);
          this.searchResults.add(row);
          count++;
          break;
        }
      }
    }
    if (count == 0) {
      System.out.println(
          "Sorry, no rows matched your search. Keep in mind the search is case-sensitive and only returns exact matches"
              + " and ensure that your inputs are in the correct order.");
    }
  }

  /**
   * Getter for the search results found for a given search.
   *
   * @return rows which matched the search
   */
  public List<List<String>> getSearchResults() {
    return this.searchResults;
  }
}
