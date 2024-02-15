package edu.brown.cs.student.main.cache;

import java.util.Collection;

public interface Searcher<RESULT, TARGET> {
  Collection<RESULT> search(TARGET target);
}
