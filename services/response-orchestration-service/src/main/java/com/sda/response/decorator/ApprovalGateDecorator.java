package com.sda.response.decorator;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.ResponseAction;

// PATTERN: Decorator (Concrete Decorator 2)
// RATIONALE: ApprovalGateDecorator adds an approval check before executing
//            critical actions. Some actions require manager approval first.
public class ApprovalGateDecorator extends ResponseActionDecorator {
    
    private boolean requiresApproval;
    
    public ApprovalGateDecorator(ResponseAction wrappedAction) {
        super(wrappedAction);
        // Critical actions require approval
        this.requiresApproval = isCriticalAction(wrappedAction.getType());
    }
    
    private boolean isCriticalAction(ResponseActionType type) {
        switch (type) {
            case ISOLATE_ENDPOINT:
            case DISABLE_USER:
                return true;  // These need approval
            case BLOCK_IP:
            case QUARANTINE_FILE:
            case ESCALATE_TO_TIER3:
            case SEND_NOTIFICATION:
            default:
                return false; // These don't need approval
        }
    }
    
    @Override
    public ActionOutcome execute(ResponseContext context) {
        System.out.println("  🔐 [APPROVAL] Checking if action requires approval...");
        
        if (!requiresApproval) {
            System.out.println("  🔐 [APPROVAL] No approval needed for: " + getType());
            return wrappedAction.execute(context);
        }
        
        System.out.println("  ⚠️ [APPROVAL] CRITICAL ACTION: " + getType() + " requires approval!");
        
        // Simulate approval check
        boolean approved = checkApproval(context);
        
        if (!approved) {
            System.out.println("  ❌ [APPROVAL] Approval DENIED! Action not executed.");
            return ActionOutcome.failure(getType(), "Approval denied for " + getType());
        }
        
        System.out.println("  ✅ [APPROVAL] Approval GRANTED! Proceeding with action.");
        return wrappedAction.execute(context);
    }
    
    private boolean checkApproval(ResponseContext context) {
        // In real system: call approval service or check with manager
        // For demo: auto-approve for critical severity, else require manual
        
        if ("CRITICAL".equals(context.getSeverity())) {
            System.out.println("  🔐 [APPROVAL] Critical severity - auto-approved");
            return true;
        }
        
        if (context.isCriticalAsset()) {
            System.out.println("  🔐 [APPROVAL] Critical asset - requires manual approval");
            // Simulate manual approval (in real system would send notification)
            return true; // For demo, always approve after 2 seconds wait
        }
        
        return true; // Default to approved for demo
    }
}