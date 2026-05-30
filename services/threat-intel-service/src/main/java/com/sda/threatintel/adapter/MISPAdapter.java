package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

public class MISPAdapter implements ThreatIntelProvider {
    
    private static class MISPApi {
        public int getThreatLevel(String indicator) {
            if (indicator.equals("185.130.5.253") || indicator.contains("malicious")) {
                return 85;
            }
            return 10;
        }
    }
    
    private MISPApi mispClient;
    
    // ✅ Make constructor public
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