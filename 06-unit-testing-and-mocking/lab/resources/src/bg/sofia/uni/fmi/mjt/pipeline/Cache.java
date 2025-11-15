package bg.sofia.uni.fmi.mjt.pipeline;

import java.util.*;

/**
 * A simple generic cache for storing key-value pairs in memory.
 * <p>
 * This class is used internally by {@link Pipeline} to cache results of previous
 * executions and avoid re-computation for the same input. The keys and values
 * are stored as {@code Object}, so type safety is not enforced at compile time.
 * Be careful when retrieving cached values to cast them to the correct type.
 */
public class Cache {

    private final Map<Object, Object> cache;

    /**
     * Creates a new, empty cache.
     */
    public Cache() {this.cache = new HashMap<>();}

    /**
     * Stores a value associated with the given key.
     * If the key already exists, the previous value is overwritten.
     *
     * @param key   the key to store
     * @param value the value associated with the key
     *
     * @throws IllegalArgumentException if key or value is null
     */
    public void cacheValue(Object key, Object value) {
        cache.put(key, value);
    }

    /**
     * Retrieves the cached value for the given key.
     *
     * @param key the key whose value is to be returned
     * @return the cached value, or {@code null} if the key is not present
     *
     * @throws IllegalArgumentException if key is null
     */
    public Object getCachedValue(Object key) {
        return cache.get(key);
    }

    /**
     * Checks if a value is cached for the given key.
     *
     * @param key the key to check
     * @return {@code true} if the cache contains the key, {@code false} otherwise
     *
     * @throws IllegalArgumentException if key is null
     */
    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    /**
     * Clears all entries from the cache.
     */
    public void clear() {
        cache.clear();
    }

    /**
     *  Checks if the cache is empty
     * */
    public boolean isEmpty() {
        return cache.isEmpty();
    }

}
