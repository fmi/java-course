package bg.sofia.uni.fmi.mjt.cache;

import java.time.LocalDateTime;

public interface Cache<K, V> {

	/**
	 * Returns the value associated with the key, if it is present in the cache and
	 * is not expired, or null otherwise. If the value is present in the cache but
	 * is expired, it is also removed from the cache
	 */
	V get(K key);

	/**
	 * Adds the value to the cache and associates it with the key, if both key and
	 * value are not null. If the key is already present in the cache, replaces the
	 * old value with the currently supplied. If the cache is full but contains at
	 * least one expired item, exactly one arbitrary expired item is removed before
	 * adding the current one. If the cache is full and there is not a single
	 * expired item, CapacityExceededException is thrown
	 *
	 * @param expiresAt
	 *            The point in time after which the item must be considered expired.
	 *            If expiresAt is null, the value should never expire.
	 * @throws CapacityExceededException
	 *             if the key is not present in the cache, the number of items
	 *             (before adding the current one) is already equal to the maximum
	 *             cache capacity and there is not a single expired item currently
	 *             in the cache
	 */
	void set(K key, V value, LocalDateTime expiresAt) throws CapacityExceededException;

	/**
	 * Returns the expiration date and time of the item with the specified key. If
	 * an item with this key is not found, returns null.
	 */
	LocalDateTime getExpiration(K key);

	/**
	 * Removes the item associated with the specified key from the cache. Returns
	 * true, if an item with the specified key was found and false otherwise.
	 */
	boolean remove(K key);

	/**
	 * Returns the number of all actual (expired or not) items stored currently in
	 * the cache.
	 */
	long size();

	/**
	 * Removes all items in the cache and resets the hit rate.
	 */
	void clear();

	/**
	 * Return the percentage of the successful hits for this cache. It is a
	 * double-precision number in the interval [0.0, 1.0] and is equal to the ratio
	 * of get(K, V) calls that returned a non-null value versus the calls that
	 * returned null. If there is not a single successful hit, the hit rate is 0.0.
	 * If there is at least one successful hit and the missed hits are zero, the hit
	 * rate is 1.0
	 */
	double getHitRate();

}