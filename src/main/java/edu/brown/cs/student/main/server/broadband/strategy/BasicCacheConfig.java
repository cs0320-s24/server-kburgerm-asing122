package edu.brown.cs.student.main.server.broadband.strategy;

import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

public class BasicCacheConfig implements CacheConfig {
  private int size;
  private int minutes;

  public BasicCacheConfig() {
    this.size = 100;
    this.minutes = 1;
  }

  @Override
  public CacheBuilder<Object, Object> configureCache() {
    return CacheBuilder.newBuilder()
        .maximumSize(size)
        .expireAfterWrite(minutes, TimeUnit.MINUTES)
        .recordStats();
  }
}