package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.exception.ItemNotFound;
import bg.sofia.uni.fmi.mjt.cache.storage.Storage;

public abstract class CacheBase<K, V> implements Cache<K, V> {
    private int capacity;
    private int totalHits;
    private int successfulHits;
    private Storage<K, V> storage;

    protected CacheBase(Storage<K, V> storage, int capacity) {
        this.storage = storage;
        this.capacity = capacity;
    }

    @Override
    public double getHitRate() {
        return totalHits == 0 ? 0 : successfulHits / (double) totalHits;
    }

    @Override
    public V get(K key) throws ItemNotFound {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        totalHits++;

        V returnValue = getFromCache(key);
        if (returnValue != null) {
            successfulHits++;
            return returnValue;
        }

        // item was not found in cache
        // will try to fetch it from primary storage
        returnValue = storage.retrieve(key);
        if (returnValue == null) {
            throw new ItemNotFound("Item with key %s not found");
        }

        addToCache(key, returnValue);
        return returnValue;
    }

    private void addToCache(K key, V value) {
        if (size() == capacity && !this.containsKey(key)) {
            evictFromCache();
        }

        put(key, value);
    }

    protected void resetHitRate() {
        this.totalHits = 0;
        this.successfulHits = 0;
    }

    abstract V getFromCache(K k);

    abstract boolean containsKey(K k);

    abstract void evictFromCache();

    abstract V put(K k, V v);
}
