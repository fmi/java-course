package bg.sofia.uni.fmi.mjt.cache.factory;

import bg.sofia.uni.fmi.mjt.cache.Cache;
import bg.sofia.uni.fmi.mjt.cache.LeastFrequentlyUsedCache;
import bg.sofia.uni.fmi.mjt.cache.LeastRecentlyUsedCache;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;

public interface CacheFactory<K, V> {
    int DEFAULT_CAPACITY = 10_000;

    /**
     * Constructs a new Cache<K, V> with the specified maximum capacity, eviction
     * policy, and storage.
     *
     * @throws IllegalArgumentException if the given capacity is less than or equal to zero
     */
    static <K, V> Cache<K, V> getInstance(Storage<K, V> storage, int capacity, EvictionPolicy policy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        return switch (policy) {
            case LEAST_FREQUENTLY_USED -> new LeastFrequentlyUsedCache<K, V>(storage, capacity);
            case LEAST_RECENTLY_USED -> new LeastRecentlyUsedCache<>(storage, capacity);
        };
    }

    /**
     * Constructs a new Cache<K, V> with maximum capacity of 10_000 items, the
     * specified eviction policy, and storage.
     */
    static <K, V> Cache<K, V> getInstance(Storage<K, V> storage, EvictionPolicy policy) {
        return switch (policy) {
            case LEAST_FREQUENTLY_USED -> new LeastRecentlyUsedCache<>(storage, DEFAULT_CAPACITY);
            case LEAST_RECENTLY_USED -> new LeastFrequentlyUsedCache<>(storage, DEFAULT_CAPACITY);
        };
    }

}
