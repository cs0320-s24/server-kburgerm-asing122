package edu.brown.cs.student;

/** Class that tests Parser functionality */
public class ParserTest {

  /** Tests basic parse functionality */
  //  @Test
  //  void testParseBasic() {
  //    try {
  //      CSVParser parser = new CSVParser(new FileReader("data/stars/ten-star.csv"), new Util(),
  // true);
  //      List<String> tenStarHeaders =
  //          new ArrayList<>(Arrays.asList("StarID", "ProperName", "X", "Y", "Z"));
  //      List<String> parsedTenStar = parser.parse();
  //      assertEquals(tenStarHeaders, parser.getHeader());
  //
  //    } catch (IOException | FactoryFailureException e) {
  //      throw new RuntimeException(e);
  //    }
  //    assertThrows(
  //        FileNotFoundException.class,
  //        () -> new CSVParser<>(new FileReader("dat0/stars/ten-star.csv"), new Util(), true));
  //  }
  //
  //  /** Tests parser can parse Strings as well as files */
  //  @Test
  //  void testParseStringReader() {
  //    StringReader reader =
  //        new StringReader(
  //            "State,Data Type,Average Weekly Earnings,Number of Workers,Earnings
  // Disparity,Employed Percent\n"
  //                + "RI,White,\" $1,058.47 \",395773.6521, $1.00 ,75%\n"
  //                + "RI,Black, $770.26 ,30424.80376, $0.73 ,6%\n"
  //                + "RI,Native American/American Indian, $471.07 ,2315.505646, $0.45 ,0%\n"
  //                + "RI,Asian-Pacific Islander,\" $1,080.09 \",18956.71657, $1.02 ,4%\n"
  //                + "RI,Hispanic/Latino, $673.14 ,74596.18851, $0.64 ,14%\n"
  //                + "RI,Multiracial, $971.89 ,8883.049171, $0.92 ,2%");
  //    CSVParser parser = new CSVParser(reader, new Util(), true);
  //    try {
  //      List<List<String>> parsedData = parser.parse();
  //      assertEquals("Asian-Pacific Islander", parsedData.get(3).get(1));
  //      assertEquals(
  //          parser.getHeader(),
  //          new ArrayList<>(
  //              Arrays.asList(
  //                  "State",
  //                  "Data Type",
  //                  "Average Weekly Earnings",
  //                  "Number of Workers",
  //                  "Earnings Disparity",
  //                  "Employed Percent")));
  //    } catch (IOException e) {
  //      throw new RuntimeException(e);
  //    } catch (FactoryFailureException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  /** Tests that parser can parse to any given object and not just Strings */
  //  @Test
  //  void testParseToObjects() {
  //    try {
  //      CSVParser parser =
  //          new CSVParser(
  //              new FileReader("data/census/dol_ri_earnings_disparity.csv"),
  //              new IncomeDatumMaker(),
  //              true);
  //      List<IncomeDatum> parsedList = parser.parse();
  //      assertEquals(parsedList.get(1).getState(), "RI");
  //      assertEquals(parsedList.get(1).getAvgWkEarnings(), " $770.26 ");
  //    } catch (IOException | FactoryFailureException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
}
