package com.sda.threatintel.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;

public class ThreatIntelCache {
    private static class CacheEntry {
        Object value;
        Instant expiry;
        
        CacheEntry(Object value, Instant expiry) {
            this.value = value;
            this.expiry = expiry;
        }
        
        boolean isExpired() {
            return Instant.now().isAfter(expiry);
        }
    }
    
    private Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private long ttlSeconds;
    
    public ThreatIntelCache(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }
    
    public void put(String key, Object value) {
        Instant expiry = Instant.now().plusSeconds(ttlSeconds);
        cache.put(key, new CacheEntry(value, expiry));
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            if (entry != null) cache.remove(key);
            return null;
        }
        return (T) entry.value;
    }
    
    public boolean containsKey(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) return false;
        if (entry.isExpired()) {
            cache.remove(key);
            return false;
        }
        return true;
    }
}