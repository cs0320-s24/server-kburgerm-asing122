package edu.brown.cs.student.main.parser.strategy;

import edu.brown.cs.student.main.parser.FactoryFailureException;

import java.util.ArrayList;
import java.util.List;

public class Util implements CreatorFromRow<List<String>> {
  /**
   * Returns the List of strings passed in.
   *
   * @param row - parsed row of CSV data separated into strings at commas
   * @return that same list
   * @throws FactoryFailureException
   */
  @Override
  public List<String> create(List<String> row) throws FactoryFailureException {
    List<String> list = new ArrayList<>();
    for (String s : row) {
      if (s.startsWith("\"") && s.endsWith("\"")) {
        list.add(s.substring(1, s.length()-1));
      } else {
       list.add(s);
      }
    }
    return list;
  }
}
