package edu.brown.cs.student.main.searcher;

import edu.brown.cs.student.main.parser.CreatorFromRow;
import edu.brown.cs.student.main.parser.FactoryFailureException;
import java.util.List;

/**
 * Class which implements creatorFromRow where T is a list of strings. Helps the Search class
 * generate a fully parsed list of strings to search through
 */
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
    return row;
  }
}
