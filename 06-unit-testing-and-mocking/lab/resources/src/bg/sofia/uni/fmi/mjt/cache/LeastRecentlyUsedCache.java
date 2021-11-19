package bg.sofia.uni.fmi.mjt.cache;

import bg.sofia.uni.fmi.mjt.cache.storage.Storage;

import java.util.Collection;
import java.util.LinkedHashMap;

public class LeastRecentlyUsedCache<K, V> extends CacheBase<K, V> {
    private final LinkedHashMap<K, V> cache;

    public LeastRecentlyUsedCache(Storage<K, V> storage, int capacity) {
        super(storage, capacity);
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void clear() {
        super.resetHitRate();
        cache.clear();
    }

    @Override
    public Collection<V> values() {
        return this.cache.values();
    }

    protected V getFromCache(K k) {
        return cache.get(k);
    }

    protected V put(K k, V v) {
        return cache.put(k, v);
    }

    protected boolean containsKey(K k) {
        return cache.containsKey(k);
    }

    protected void evictFromCache() {
        var it = cache.keySet().iterator();
        if (it.hasNext()) {
            it.remove();
        }
    }

}
