package com.sda.response.strategy;

import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import java.util.ArrayList;
import java.util.List;

// PATTERN: Strategy (Concrete Strategy 2)
// RATIONALE: BalancedResponseStrategy takes a measured approach.
//            Prioritizes actions based on severity and asset criticality.
public class BalancedResponseStrategy implements ResponseStrategy {
    
    @Override
    public List<ResponseActionType> determineActions(ResponseContext context) {
        List<ResponseActionType> actions = new ArrayList<>();
        
        // First priority: isolate critical assets
        if (context.isCriticalAsset()) {
            actions.add(ResponseActionType.ISOLATE_ENDPOINT);
        }
        
        // Block IP only if malicious or multiple attempts
        if (context.getSourceIp() != null && 
            ("HIGH".equals(context.getSeverity()) || "CRITICAL".equals(context.getSeverity()))) {
            actions.add(ResponseActionType.BLOCK_IP);
        }
        
        // Disable user only for confirmed threats
        if (context.getSuspectUserId() != null && "CRITICAL".equals(context.getSeverity())) {
            actions.add(ResponseActionType.DISABLE_USER);
        }
        
        // Always notify on high/critical
        actions.add(ResponseActionType.SEND_NOTIFICATION);
        
        System.out.println("⚖️ BALANCED STRATEGY: Taking " + actions.size() + " prioritized actions");
        return actions;
    }
    
    @Override
    public String getName() {
        return "Balanced Response";
    }
    
    @Override
    public String getDescription() {
        return "Prioritize critical assets first, then take measured response actions";
    }
    
    @Override
    public int getPriority() {
        return 2;
    }
}