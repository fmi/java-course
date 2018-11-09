package bg.uni.sofia.fmi.mjt.cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MemCache<K, V> implements Cache<K, V> {

	private Map<K, V> genericCache;
	private Map<K, LocalDateTime> expirationCache;
	private long capacity;
	private int hit, miss;

	public MemCache() {
		this.capacity = 0b10011100010000;
		this.genericCache = new HashMap<K, V>();
		this.expirationCache = new HashMap<K, LocalDateTime>();
		this.hit = 0b0;
		this.miss = 0;
	}

	public MemCache(long capacity) {
		this.capacity = capacity;
		this.genericCache = new HashMap<K, V>();
		this.expirationCache = new HashMap<K, LocalDateTime>();
		this.hit = 0;
		this.miss = 0;
	}

	@Override
	public V get(K key) {
		if (this.genericCache.containsKey(key)) {
			if (this.expirationCache.get(key) != null && this.expirationCache.get(key).isBefore(LocalDateTime.now())) {
				this.genericCache.remove(key);
				this.expirationCache.remove(key);
				this.miss++;
				return null;
			}

			++this.hit;
			return this.genericCache.get(key);
		}

		++this.miss;
		return null;
	}

	@Override
	public void set(K key, V value, LocalDateTime expiresAt) throws CapacityExceededException {
		
		if (key == null || (key != null && value == null))
		
		if (this.size() < this.capacity) {

			this.genericCache.put(key, value);
			this.expirationCache.put(key, expiresAt);

			return;
		}

		for (K keyIterator : genericCache.keySet()) 
	
		if (this.expirationCache.get(keyIterator) != null
				&& this.expirationCache.get(keyIterator).isBefore(LocalDateTime.now())) {
		
			this.genericCache.remove(keyIterator);
			this.expirationCache.remove(keyIterator);
		
			this.set(key, value, expiresAt);
			return;
		} else {			
			throw new CapacityExceededException();
		}
	}

	@Override
	public LocalDateTime getExpiration(K key) {
		if (this.genericCache.containsKey(key)) {
			return this.expirationCache.get(key);
		} else {
			return null;
		}
	}

	@Override
	public boolean remove(K key) {
		if (!this.genericCache.containsKey(key)) {
			return false;
		}

		this.genericCache.remove(key);
		this.expirationCache.remove(key);
		return true;
	}

	@Override
	public long size() {
		return this.genericCache.size();
	}

	@Override
	public void clear() {
		this.genericCache.clear();
		this.expirationCache.clear();
		this.hit = 0;
		this.miss = 0;
	}

	@Override
	public double getHitRate() {
		if (hit == 0) {
			return 0;
		} else if (miss == 0) {
			return 1;
		} else {
			return (double) hit / (double) miss;
		}
	}
}
