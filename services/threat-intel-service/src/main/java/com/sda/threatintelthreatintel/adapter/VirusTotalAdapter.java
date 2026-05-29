package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

// PATTERN: Adapter (Adaptee -> Target)
// RATIONALE: Converts VirusTotal's proprietary API response to our canonical ReputationResult
public class VirusTotalAdapter implements ThreatIntelProvider {
    
    // Mocking the external VirusTotal client (in real project, you'd use actual SDK)
    private static class VirusTotalAPI {
        // Simulated API call
        public int getPositives(String hash) {
            // Mock data - in real implementation, this would be an HTTP call
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
        // Simulate API call to VirusTotal
        int positives = vtClient.getPositives(indicator);
        int total = vtClient.getTotal();
        double score = (double) positives / total;
        boolean malicious = positives > 5;
        
        // Convert VirusTotal format → our canonical format
        return ReputationResult.builder()
            .indicator(indicator)
            .malicious(malicious)
            .score(score)
            .source("VirusTotal")
            .build();
    }
}