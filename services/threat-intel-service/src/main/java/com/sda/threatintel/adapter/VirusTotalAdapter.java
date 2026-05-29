package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

public class VirusTotalAdapter implements ThreatIntelProvider {
    
    private static class VirusTotalAPI {
        public int getPositives(String hash) {
            return hash.contains("malicious") ? 45 : 2;
        }
        public int getTotal() {
            return 70;
        }
    }
    
    private VirusTotalAPI vtClient;
    
    public VirusTotalAdapter() {
        this.vtClient = new VirusTotalAPI();
    }
    
    @Override
    public ReputationResult checkReputation(String indicator, IndicatorType type) {
        int positives = vtClient.getPositives(indicator);
        int total = vtClient.getTotal();
        double score = (double) positives / total;
        boolean malicious = positives > 5;

        return ReputationResult.builder()
            .indicator(indicator)
            .malicious(malicious)
            .score(score)
            .source("VirusTotal")
            .build();
    }
}