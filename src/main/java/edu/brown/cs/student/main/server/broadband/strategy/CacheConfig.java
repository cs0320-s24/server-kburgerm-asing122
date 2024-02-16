package edu.brown.cs.student.main.server.broadband.strategy;

import com.google.common.cache.CacheBuilder;

public interface CacheConfig {
  CacheBuilder<Object, Object> configureCache();
}
