package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.exception.ItemNotFound;

import java.util.Collection;

public interface Cache<K, V> {

    /**
     * @param key
     * @return the value associated with the key, if it is available either in the cache, or in the storage.
     * @throws ItemNotFound if the item is not available
     */
    V get(K key) throws ItemNotFound;

    /**
     * @return the number of all actual items stored currently in the cache.
     */
    int size();

    /**
     * Removes all items in the cache and resets the hit rate.
     */
    void clear();

    /**
     * @return the percentage of the successful hits for this cache. It is a
     * double-precision number in the interval [0.0, 1.0] and is equal to the proportion
     * of get(K) calls that returned a value found in the cache (without reverting to
     * the primary storage) versus the total number of calls. If there is not a single successful hit,
     * the hit rate is 0.0. If there is at least one successful hit and the missed hits are zero,
     * the hit rate is 1.0
     */
    double getHitRate();

    /**
     * @return unmodifiable collection of the values in the cache.
     */
    Collection<V> values();
}
