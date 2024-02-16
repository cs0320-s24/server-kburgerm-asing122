package edu.brown.cs.student.main.parser.strategy;

/** Represents a data point of income data used for testing */
public class IncomeDatum {
  private String state;
  private String dataType;
  private String avgWkEarnings;
  private String numWorkers;
  private String earnDisparity;
  private String empPercent;

  /**
   * Constructor for an IncomeDatum object
   *
   * @param state
   * @param dataType
   * @param avgWkEarnings
   * @param numWorkers
   * @param earnDisparity
   * @param empPercent
   */
  public IncomeDatum(
      String state,
      String dataType,
      String avgWkEarnings,
      String numWorkers,
      String earnDisparity,
      String empPercent) {
    this.state = state;
    this.dataType = dataType;
    this.avgWkEarnings = avgWkEarnings;
    this.numWorkers = numWorkers;
    this.earnDisparity = earnDisparity;
    this.empPercent = empPercent;
  }

  /**
   * @return average weekly earnings
   */
  public String getAvgWkEarnings() {
    return avgWkEarnings;
  }

  /**
   * @return the datatype
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * @return the earning disparity
   */
  public String getEarnDisparity() {
    return earnDisparity;
  }

  /**
   * @return percent employed
   */
  public String getEmpPercent() {
    return empPercent;
  }

  /**
   * @return number of workers
   */
  public String getNumWorkers() {
    return numWorkers;
  }

  /**
   * @return state for the data point
   */
  public String getState() {
    return state;
  }
}
