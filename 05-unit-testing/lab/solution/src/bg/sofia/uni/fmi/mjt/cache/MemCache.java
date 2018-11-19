package bg.sofia.uni.fmi.mjt.cache;

import java.time.LocalDateTime;
import java.util.HashMap;

import java.util.Map;

public class MemCache<K, V> implements Cache<K, V> {

	private long maxCapacity;

	private long size = 0;
	private Map<K, CacheItem<V>> map;

	private long successfulHits = 0;
	private long missedHits = 0;

	/**
	 * Constructs a new Cache with the specified maximum capacity (in number of
	 * stored items) and default expiration (in seconds)
	 */
	public MemCache(long capacity) {
		this.maxCapacity = capacity;

		this.map = new HashMap<>();
	}

	/**
	 * Constructs a new Cache with maximum capacity of 10000 items
	 */
	public MemCache() {
		this(10_000);
	}

	@Override
	public V get(K key) {
		CacheItem<V> ci = map.get(key);
		if (ci != null) {
			if (!ci.isExpired()) {
				successfulHits++;
				return ci.getValue();
			} else {
				map.remove(key);
				size--;
			}
		}
		missedHits++;
		return null;
	}

	@Override
	public void set(K key, V value, LocalDateTime expiresAt) throws CapacityExceededException {
		if (key == null || value == null) {
			return;
		}

		if (map.containsKey(key)) {
			map.put(key, new CacheItem<>(value, expiresAt));
			return;
		}

		if (size < maxCapacity || removeOneExpired()) {
			map.put(key, new CacheItem<>(value, expiresAt));
			size++;
			return;
		}

		throw new CapacityExceededException("Cache is full, nothing expired to free up space.");
	}

	@Override
	public LocalDateTime getExpiration(K key) {
		CacheItem<V> item = map.get(key);
		if (item == null) {
			return null;
		}
		return item.getExpiration();
	}

	private boolean removeOneExpired() {
		for (K key : map.keySet()) {
			if (map.get(key).isExpired()) {
				map.remove(key);
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(K key) {
		if (map.containsKey(key)) {
			map.remove(key);
			size--;
			return true;
		}
		return false;
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public void clear() {
		map.clear();
		size = 0;
		successfulHits = 0;
		missedHits = 0;
	}

	@Override
	public double getHitRate() {
		if (successfulHits == 0) {
			return 0.0;
		}
		return (double) successfulHits / (successfulHits + missedHits);
	}

}
