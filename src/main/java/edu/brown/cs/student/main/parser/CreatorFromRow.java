package edu.brown.cs.student.main.parser;

import java.util.List;

/**
 * This interface defines a method that allows your CSV parser to convert each row into an object of
 * some arbitrary passed type.
 *
 * <p>Your parser class constructor should take a second parameter of this generic interface type.
 */

// This will be general,
public interface CreatorFromRow<T> {

  /**
   * Creates and returns an instance of type T based on input list of Strings.
   *
   * @param row - parsed row of CSV data separated into strings at commas
   * @return an object of type T based on the input data
   * @throws FactoryFailureException
   */
  T create(List<String> row) throws FactoryFailureException;
}
