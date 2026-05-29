package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

// PATTERN: Adapter (Another Adaptee implementation)
// RATIONALE: Converts MISP's threat feed format to our canonical ReputationResult
public class MISPAdapter implements ThreatIntelProvider {
    
    // Mock MISP client
    private static class MISPApi {
        public int getThreatLevel(String indicator) {
            // Mock data
            return indicator.equals("185.130.5.253") ? 85 : 10;
        }
    }
    
    private MISPApi mispClient;
    
    public MISPAdapter() {
        this.mispClient = new MISPApi();
    }
    
    @Override
    public ReputationResult checkReputation(String indicator, IndicatorType type) {
        int threatLevel = mispClient.getThreatLevel(indicator);
        double score = threatLevel / 100.0;
        boolean malicious = threatLevel > 60;
        
        return ReputationResult.builder()
            .indicator(indicator)
            .malicious(malicious)
            .score(score)
            .source("MISP")
            .build();
    }
}