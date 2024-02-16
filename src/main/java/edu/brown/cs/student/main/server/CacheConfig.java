package edu.brown.cs.student.main.server;

import com.google.common.cache.CacheBuilder;

public interface CacheConfig {
  CacheBuilder<Object, Object> configureCache();
}
