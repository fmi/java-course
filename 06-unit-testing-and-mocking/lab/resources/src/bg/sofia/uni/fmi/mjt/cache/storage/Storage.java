package bg.sofia.uni.fmi.mjt.cache.storage;

public interface Storage<K, V> {

    /**
     * Adds the value to the storage and associates it with the key, if both key and
     * value are not null. If key or value (or both) are null, the method does
     * nothing. If the key is already present in the store, replaces the old value
     * with the currently supplied and returns the old value.
     *
     * @param key
     * @param value
     * @return the old value if the key was already present or null otherwise.
     */
    V store(K key, V value);

    /**
     * @param key
     * @return the value associated with the given key, or null if it does not exist.
     */
    V retrieve(K key);

    /**
     * Removes the key-value association from the store.
     *
     * @param key
     * @return the value associated with the given key, or null if it does not exist.
     */
    V remove(K key);

}
