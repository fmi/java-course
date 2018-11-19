package bg.sofia.uni.fmi.mjt.cache;

import java.time.LocalDateTime;

public class CacheItem<V> {

    private V value;
    private LocalDateTime expiration;
    
    public CacheItem(V value, LocalDateTime expiration) {
        this.value = value;
        this.expiration = expiration;
    }

    public V getValue() {
        return value;
    }
    
    public LocalDateTime getExpiration() {
        return expiration;
    }
    
    public boolean isExpired() {
        return expiration != null && LocalDateTime.now().isAfter(expiration);
    }
    
}
