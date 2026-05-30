package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

public class AdapterTest {
    public static void main(String[] args) {
        System.out.println("\n========== TEST 1: Adapter Pattern ==========\n");
        
        // Test VirusTotal Adapter
        System.out.println("--- Testing VirusTotal Adapter ---");
        ThreatIntelProvider vtAdapter = new VirusTotalAdapter();
        ReputationResult vtResult = vtAdapter.checkReputation("malicious-file.exe", IndicatorType.FILE_HASH);
        
        System.out.println("Indicator: " + vtResult.getIndicator());
        System.out.println("Source: " + vtResult.getSource());
        System.out.println("Malicious: " + vtResult.isMalicious());
        System.out.println("Score: " + vtResult.getScore());
        System.out.println("Verdict: " + vtResult.getVerdict());
        
        // Test MISP Adapter
        System.out.println("\n--- Testing MISP Adapter ---");
        ThreatIntelProvider mispAdapter = new MISPAdapter();
        ReputationResult mispResult = mispAdapter.checkReputation("185.130.5.253", IndicatorType.IP_ADDRESS);
        
        System.out.println("Indicator: " + mispResult.getIndicator());
        System.out.println("Source: " + mispResult.getSource());
        System.out.println("Malicious: " + mispResult.isMalicious());
        System.out.println("Score: " + mispResult.getScore());
        System.out.println("Verdict: " + mispResult.getVerdict());
        
        System.out.println("\n✅ Adapter Pattern Test Complete!\n");
    }
}