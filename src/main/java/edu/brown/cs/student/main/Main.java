package edu.brown.cs.student.main;

import edu.brown.cs.student.main.searcher.Search;

/** The Main class of our project. This is where execution begins. */
public final class Main {
  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  /**
   * Called upon running the program. Interacts with the user to generate a search
   *
   * @param args - command-line arguments
   */
  private Main(String[] args) {
    int len = args.length;
    switch (len) {
      case 0, 1, 2:
        System.out.println(
            "Enter your search as follows: <file name> <search term> <'yes' if header row in csv, 'no' for no header>"
                + " <column to search (optional)>");
        break;
      case 3:
        if (args[2].equals("yes")) {
          new Search(args[0], args[1], true);
        } else {
          new Search(args[0], args[1], false);
        }
        break;

      case 4:
        try {
          int col = Integer.parseInt(args[3]);
          if (args[2].equals("yes")) {
            new Search(args[0], args[1], true, col);
          } else {
            new Search(args[0], args[1], false, col);
          }
        } catch (NumberFormatException e) {
          if (args[2].equals("yes")) {
            new Search(args[0], args[1], true, args[3]);
          } else {
            new Search(args[0], args[1], false, args[3]);
          }
        }
        break;
      default:
        System.out.println(
            "Enter your search as follows: <file name> <search term> <column to search (optional)>");
    }
  }

  private void run() {}
}
