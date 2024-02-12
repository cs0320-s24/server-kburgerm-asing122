package edu.brown.cs.student;

import edu.brown.cs.student.main.parser.CreatorFromRow;
import edu.brown.cs.student.main.parser.FactoryFailureException;
import java.util.List;

/**
 * Class that implements CreatorFromRow and creates IncomeDatum objects, used for testing purposes
 */
public class IncomeDatumMaker implements CreatorFromRow<IncomeDatum> {

  /**
   * Creates an IncomeDatum object based on the list data passed in
   *
   * @param row - parsed row of CSV data separated into strings at commas
   * @return
   * @throws FactoryFailureException
   */
  @Override
  public IncomeDatum create(List<String> row) throws FactoryFailureException {
    return new IncomeDatum(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5));
  }
}
