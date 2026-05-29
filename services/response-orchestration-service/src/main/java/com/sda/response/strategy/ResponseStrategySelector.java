package com.sda.response.strategy;

import com.sda.response.domain.ResponseContext;

public class ResponseStrategySelector {
    
    private AggressiveContainmentStrategy aggressiveStrategy;
    private BalancedResponseStrategy balancedStrategy;
    private ConservativeStrategy conservativeStrategy;
    
    public ResponseStrategySelector() {
        this.aggressiveStrategy = new AggressiveContainmentStrategy();
        this.balancedStrategy = new BalancedResponseStrategy();
        this.conservativeStrategy = new ConservativeStrategy();
    }
    
    public ResponseStrategy selectStrategy(ResponseContext context) {
        String severity = context.getSeverity();
        boolean isCriticalAsset = context.isCriticalAsset();
        
        if ("CRITICAL".equals(severity) && isCriticalAsset) {
            System.out.println("🎯 Strategy Selection: CRITICAL incident on CRITICAL asset → AGGRESSIVE");
            return aggressiveStrategy;
        }
        
        if ("CRITICAL".equals(severity) || "HIGH".equals(severity)) {
            System.out.println("🎯 Strategy Selection: " + severity + " severity → BALANCED");
            return balancedStrategy;
        }
        
        System.out.println("🎯 Strategy Selection: " + severity + " severity → CONSERVATIVE");
        return conservativeStrategy;
    }
    
    public ResponseStrategy selectStrategyByName(String strategyName) {
        switch (strategyName.toLowerCase()) {
            case "aggressive":
                return aggressiveStrategy;
            case "balanced":
                return balancedStrategy;
            case "conservative":
                return conservativeStrategy;
            default:
                return balancedStrategy;
        }
    }
    
    // ✅ Add this missing method
    public void printAvailableStrategies() {
        System.out.println("\n📋 Available Response Strategies:");
        System.out.println("   1. " + aggressiveStrategy.getName() + " - " + aggressiveStrategy.getDescription());
        System.out.println("   2. " + balancedStrategy.getName() + " - " + balancedStrategy.getDescription());
        System.out.println("   3. " + conservativeStrategy.getName() + " - " + conservativeStrategy.getDescription());
    }
}