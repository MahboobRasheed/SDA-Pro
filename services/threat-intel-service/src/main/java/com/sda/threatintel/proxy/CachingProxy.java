package com.sda.threatintel.proxy;

import com.sda.threatintel.adapter.ThreatIntelProvider;
import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

// PATTERN: Proxy (Caching Proxy)
// RATIONALE: Intercepts requests to check cache before calling real provider,
//            reducing expensive external API calls and improving response time.
public class CachingProxy implements ThreatIntelProvider {
    
    private ThreatIntelProvider realProvider;
    private ThreatIntelCache cache;
    
    public CachingProxy(ThreatIntelProvider realProvider) {
        this.realProvider = realProvider;
        this.cache = new ThreatIntelCache(3600); // 1 hour TTL
    }
    
    @Override
    public ReputationResult checkReputation(String indicator, IndicatorType type) {
        String cacheKey = type.name() + ":" + indicator;
        
        // Check cache first
        ReputationResult cached = cache.get(cacheKey);
        if (cached != null) {
            System.out.println("Cache HIT for: " + cacheKey);
            return cached;
        }
        
        System.out.println("Cache MISS for: " + cacheKey + " - calling real provider");
        
        // Call real provider
        ReputationResult result = realProvider.checkReputation(indicator, type);
        
        // Store in cache
        cache.put(cacheKey, result);
        
        return result;
    }
}