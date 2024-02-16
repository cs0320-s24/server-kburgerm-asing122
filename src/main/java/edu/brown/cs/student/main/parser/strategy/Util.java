package edu.brown.cs.student.main.parser.strategy;

import edu.brown.cs.student.main.parser.FactoryFailureException;
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
            return row;
        }
    }

