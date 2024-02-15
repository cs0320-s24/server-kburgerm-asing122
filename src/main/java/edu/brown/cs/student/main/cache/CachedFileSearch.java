// package edu.brown.cs.student.main.cache;
//
// import java.util.Collection;
//
// import com.google.common.cache.CacheBuilder;
// import com.google.common.cache.CacheLoader;
// import com.google.common.cache.LoadingCache;
// import java.util.concurrent.TimeUnit;
//
// public class CachedFileSearch implements Searcher<String, String> {
//    private final Searcher<String, String> wrappedSearcher;
//    private final LoadingCache<String, Collection<String>> cache;
//
//    /**
//     * Proxy class: wrap an instance of Searcher (of any kind) and cache
//     * its results.
//     *
//     * There are _many_ ways to implement this! We could use a plain
//     * HashMap, but then we'd have to handle "eviction" ourselves.
//     * Lots of libraries exist. We're using Guava here, to demo the
//     * strategy pattern.
//     *
//     * @param toWrap the Searcher to wrap
//     */
//    public CachedFileSearch(Searcher<String, String> toWrap, int mins, int size) {
//      this.wrappedSearcher = toWrap;
//
//      // Look at the docs -- there are lots of builder parameters you can use
//      //   including ones that affect garbage-collection (not needed for Server).
//      this.cache = CacheBuilder.newBuilder()
//          // How many entries maximum in the cache?
//          .maximumSize(size)
//          // How long should entries remain in the cache?
//          .expireAfterWrite(mins, TimeUnit.MINUTES)
//          // Keep statistical info around for profiling purposes
//          .recordStats()
//          .build(
//              // Strategy pattern: how should the cache behave when
//              // it's asked for something it doesn't have?
//              new CacheLoader<>() {
//                @Override
//                public Collection<String> load(String key)  {
//                  System.out.println("called load for: "+key);
//                  // If this isn't yet present in the cache, load it:
//                  return wrappedSearcher.search(key);
//                }
//              });
//    }
//
//    @Override
//    public Collection<String> search(String target) {
//      // "get" is designed for concurrent situations; for today, use getUnchecked:
//      Collection<String> result = cache.getUnchecked(target);
//      // For debugging and demo (would remove in a "real" version):
//      System.out.println(cache.stats());
//      return result;
//    }
//  }
//  */
// }
