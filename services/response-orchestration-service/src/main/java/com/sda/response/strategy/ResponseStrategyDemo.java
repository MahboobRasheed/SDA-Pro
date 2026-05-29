package com.sda.response.strategy;

import com.sda.response.domain.ResponseContext;
import com.sda.response.domain.ResponseActionType;

// Demo class to test all strategies
public class ResponseStrategyDemo {
    
    public static void main(String[] args) {
        System.out.println("\n========== STRATEGY PATTERN DEMO ==========\n");
        
        ResponseStrategySelector selector = new ResponseStrategySelector();
        selector.printAvailableStrategies();
        
        // Test Case 1: Critical incident on critical asset
        System.out.println("\n--- TEST CASE 1: Critical incident on critical asset ---");
        ResponseContext criticalContext = new ResponseContext("INC-001", "CRITICAL")
            .withSourceIp("185.130.5.253")
            .withAffectedEndpoint("finance-server-01")
            .withSuspectUserId("user123")
            .withCriticalAsset(true);
        
        ResponseStrategy strategy1 = selector.selectStrategy(criticalContext);
        System.out.println("Selected: " + strategy1.getName());
        System.out.println("Actions: " + strategy1.determineActions(criticalContext));
        
        // Test Case 2: High severity incident
        System.out.println("\n--- TEST CASE 2: High severity incident ---");
        ResponseContext highContext = new ResponseContext("INC-002", "HIGH")
            .withSourceIp("45.33.22.11")
            .withAffectedEndpoint("workstation-05")
            .withCriticalAsset(false);
        
        ResponseStrategy strategy2 = selector.selectStrategy(highContext);
        System.out.println("Selected: " + strategy2.getName());
        System.out.println("Actions: " + strategy2.determineActions(highContext));
        
        // Test Case 3: Low severity incident
        System.out.println("\n--- TEST CASE 3: Low severity incident ---");
        ResponseContext lowContext = new ResponseContext("INC-003", "LOW")
            .withSourceIp("192.168.1.100")
            .withCriticalAsset(false);
        
        ResponseStrategy strategy3 = selector.selectStrategy(lowContext);
        System.out.println("Selected: " + strategy3.getName());
        System.out.println("Actions: " + strategy3.determineActions(lowContext));
        
        // Test Case 4: Manual override
        System.out.println("\n--- TEST CASE 4: Manual strategy override ---");
        ResponseStrategy manualStrategy = selector.selectStrategyByName("aggressive");
        System.out.println("Manual selection: " + manualStrategy.getName());
        
        System.out.println("\n========== STRATEGY DEMO COMPLETE ==========");
    }
}