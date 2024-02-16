package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

import edu.brown.cs.student.main.searcher.Search;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.testng.AssertJUnit;

/** Class that tests Search functionality */
public class SearchTest {

  /** Tests basic search functionality */
  @Test
  void testSearchBasic() {
        Search searchStringCol =
            new Search("data/census/dol_ri_earnings_disparity.csv", "RI", true, "State");
        assertEquals("RI", searchStringCol.getSearchResults().get(0).get(0));
        Search searchTenStar = new Search("data/stars/ten-star.csv", "Sol", true, "ProperName");
        assertEquals("Sol", searchTenStar.getSearchResults().get(0).get(1));
  }

  /** Tests search when no column identifier is given */
  @Test
  void testSearchNoCol() {
        Search searchNoCol = new Search("data/census/dol_ri_earnings_disparity.csv", "RI", true);
        assertEquals("RI", searchNoCol.getSearchResults().get(0).get(0));
        assertEquals("Asian-Pacific Islander", searchNoCol.getSearchResults().get(3).get(1));
  }

  /** Tests search when an integer column index is given */
  @Test
  void testSearchIntCol() {
        Search searchIntCol =
            new Search("data/census/dol_ri_earnings_disparity.csv", "Asian-Pacific Islander",
     true, 1);
        assertEquals(" $1.02 ", searchIntCol.getSearchResults().get(0).get(4));
  }

  /** Tests searching for a value which isn't there */
  @Test
  void testSearchNotThere() {
        Search searchNone =
            new Search("data/census/dol_ri_earnings_disparity.csv", "Nigerian", true, 1);
        assertEquals(0, searchNone.getSearchResults().size());
  }

  @Test
  void testFindRowWithoutHeaders() {
    Search csvSearch = new Search("data/census/income_by_race.csv", "18499", false);
    String[] answer = new String[] {"9", "Hispanic", "2013", "2013", "53856", "18499", "\"Washington County, RI\"", "05000US44009", "washington-county-ri"};
    assertEquals(Arrays.asList(answer), csvSearch.getSearchResults().get(0));
  }

  @Test
  void testFindRowWithHeaders() {
    Search csvSearch =
        new Search("data/census/dol_ri_earnings_disparity.csv", "White", true, "Data Type");
    String[] answer = new String[] {"RI", "White", "\" $1,058.47 \"", "395773.6521", " $1.00 ", "75%"};
    AssertJUnit.assertEquals(Arrays.asList(answer), csvSearch.getSearchResults().get(0));
  }

  @Test
  void testFindRowNoMatch() {
    Search csvSearch = new Search("data/census/dol_ri_earnings_disparity.csv", "Asian", true, "Data Type");
    AssertJUnit.assertEquals(0, csvSearch.getSearchResults().size());
  }

  @Test
  void testFindMultipleRows() {
    Search csvSearch = new Search("data/census/postsecondary_education.csv", "Two or More Races", true, "IPEDS Race");
    String[] row1 = new String[] {"Two or More Races", "2020", "2020", "217156", "Brown University", "58", "brown-university", "0.018764154", "Men", "1"};
    String[] row2 = new String[] {"Two or More Races", "2020", "2020", "217156", "Brown University", "85", "brown-university", "0.027499191", "Women", "2"};
    AssertJUnit.assertEquals(Arrays.asList(Arrays.asList(row1), Arrays.asList(row2)),
        csvSearch.getSearchResults());
  }

  @Test
  void testByColumnIndex() {
    Search csvSearch = new Search("data/census/dol_ri_earnings_disparity.csv", "White", true, 1);
    String[] answer = new String[] {"RI", "White", "\" $1,058.47 \"", "395773.6521", " $1.00 ", "75%"};
    AssertJUnit.assertEquals(Arrays.asList(answer), csvSearch.getSearchResults().get(0));
  }

  @Test
  void testPresentValuesWrongColumn() {
    Search csvSearch = new Search("data/census/postsecondary_education.csv", "2020", true, "IPEDS Race");
    AssertJUnit.assertEquals(0, csvSearch.getSearchResults().size());
  }
}
