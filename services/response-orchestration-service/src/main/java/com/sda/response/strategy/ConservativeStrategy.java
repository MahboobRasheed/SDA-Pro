package com.sda.response.strategy;

import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import java.util.ArrayList;
import java.util.List;

// PATTERN: Strategy (Concrete Strategy 3)
// RATIONALE: ConservativeStrategy takes minimal actions, mostly notification-based.
//            Used for low-severity incidents or when business impact is high.
public class ConservativeStrategy implements ResponseStrategy {
    
    @Override
    public List<ResponseActionType> determineActions(ResponseContext context) {
        List<ResponseActionType> actions = new ArrayList<>();
        
        // Only notify - no automated blocking
        actions.add(ResponseActionType.SEND_NOTIFICATION);
        
        // Only escalate if multiple conditions met
        if ("CRITICAL".equals(context.getSeverity()) && context.isCriticalAsset()) {
            actions.add(ResponseActionType.ESCALATE_TO_TIER3);
        }
        
        // Only isolate if absolutely necessary (rare for conservative)
        if (context.isCriticalAsset() && "CRITICAL".equals(context.getSeverity())) {
            // Add isolation as last resort, but with warning
            System.out.println("⚠️ CONSERVATIVE WARNING: Considering isolation for critical asset");
            // actions.add(ResponseActionType.ISOLATE_ENDPOINT); // Commented - conservative means avoid isolation
        }
        
        System.out.println("🛡️ CONSERVATIVE STRATEGY: Taking " + actions.size() + " cautious actions");
        return actions;
    }
    
    @Override
    public String getName() {
        return "Conservative (Watch & Wait)";
    }
    
    @Override
    public String getDescription() {
        return "Minimal automated actions - prioritize notification and manual review";
    }
    
    @Override
    public int getPriority() {
        return 3;
    }
}