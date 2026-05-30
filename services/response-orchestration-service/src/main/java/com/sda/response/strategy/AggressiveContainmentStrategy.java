package com.sda.response.strategy;

import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import java.util.ArrayList;
import java.util.List;

// PATTERN: Strategy (Concrete Strategy 1)
// RATIONALE: AggressiveContainmentStrategy takes immediate, forceful actions
//            for critical incidents. Isolates endpoints and blocks IPs without delay.
public class AggressiveContainmentStrategy implements ResponseStrategy {
    
    @Override
    public List<ResponseActionType> determineActions(ResponseContext context) {
        List<ResponseActionType> actions = new ArrayList<>();
        
        // Always isolate affected endpoint first
        if (context.getAffectedEndpoint() != null) {
            actions.add(ResponseActionType.ISOLATE_ENDPOINT);
        }
        
        // Block source IP immediately
        if (context.getSourceIp() != null) {
            actions.add(ResponseActionType.BLOCK_IP);
        }
        
        // Disable user account if suspicious activity
        if (context.getSuspectUserId() != null) {
            actions.add(ResponseActionType.DISABLE_USER);
        }
        
        // Escalate to Tier-3 for critical incidents
        if ("CRITICAL".equals(context.getSeverity())) {
            actions.add(ResponseActionType.ESCALATE_TO_TIER3);
        }
        
        System.out.println("🔥 AGGRESSIVE STRATEGY: Taking " + actions.size() + " immediate actions");
        return actions;
    }
    
    @Override
    public String getName() {
        return "Aggressive Containment";
    }
    
    @Override
    public String getDescription() {
        return "Immediately isolate, block, and disable - zero hesitation for critical threats";
    }
    
    @Override
    public int getPriority() {
        return 1; // Highest priority
    }
}