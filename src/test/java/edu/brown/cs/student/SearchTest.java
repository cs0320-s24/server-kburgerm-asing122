package edu.brown.cs.student;

import org.junit.jupiter.api.Test;

/** Class that tests Search functionality */
public class SearchTest {

  /** Tests basic search functionality */
  @Test
  void testSearchBasic() {
    //    Search searchStringCol =
    //        new Search("data/census/dol_ri_earnings_disparity.csv", "RI", true, "State");
    //    assertEquals("RI", searchStringCol.getSearchResults().get(0).get(0));
    //    Search searchTenStar = new Search("data/stars/ten-star.csv", "Sol", true, "ProperName");
    //    assertEquals("Sol", searchTenStar.getSearchResults().get(0).get(1));
  }

  /** Tests search when no column identifier is given */
  @Test
  void testSearchNoCol() {
    //    Search searchNoCol = new Search("data/census/dol_ri_earnings_disparity.csv", "RI", true);
    //    assertEquals("RI", searchNoCol.getSearchResults().get(0).get(0));
    //    assertEquals("Asian-Pacific Islander", searchNoCol.getSearchResults().get(3).get(1));
  }

  /** Tests search when an integer column index is given */
  @Test
  void testSearchIntCol() {
    //    Search searchIntCol =
    //        new Search("data/census/dol_ri_earnings_disparity.csv", "Asian-Pacific Islander",
    // true, 1);
    //    assertEquals(" $1.02 ", searchIntCol.getSearchResults().get(0).get(4));
  }

  /** Tests searching for a value which isn't there */
  @Test
  void testSearchNotThere() {
    //    Search searchNone =
    //        new Search("data/census/dol_ri_earnings_disparity.csv", "Nigerian", true, 1);
    //    assertEquals(0, searchNone.getSearchResults().size());
  }
}
