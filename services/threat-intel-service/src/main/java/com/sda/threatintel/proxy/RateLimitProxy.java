package com.sda.threatintel.proxy;

import com.sda.threatintel.adapter.ThreatIntelProvider;
import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.Instant;

// PATTERN: Proxy (Rate Limiting Proxy)
// RATIONALE: Controls the rate of requests to external APIs,
//            preventing quota exhaustion and respecting API limits.
public class RateLimitPwroxy implements ThreatIntelProvider {
    
    private ThreatIntelProvider realProvider;
    private int maxRequestsPerMinute;
    private AtomicInteger requestCount;
    private Instant windowStart;
    
    public RateLimitProxy(ThreatIntelProvider realProvider, int maxRequestsPerMinute) {
        this.realProvider = realProvider;
        this.maxRequestsPerMinute = maxRequestsPerMinute;
        this.requestCount = new AtomicInteger(0);
        this.windowStart = Instant.now();
    }
    
    @Override
    public ReputationResult checkReputation(String indicator, IndicatorType type) {
        synchronized (this) {
            Instant now = Instant.now();
            
            // Reset counter if window has passed
            if (now.isAfter(windowStart.plusSeconds(60))) {
                requestCount.set(0);
                windowStart = now;
            }
            
            // Check rate limit
            if (requestCount.get() >= maxRequestsPerMinute) {
                throw new RuntimeException("Rate limit exceeded! Max " + 
                    maxRequestsPerMinute + " requests per minute.");
            }
            
            // Increment counter
            requestCount.incrementAndGet();
        }
        
        return realProvider.checkReputation(indicator, type);
    }
}