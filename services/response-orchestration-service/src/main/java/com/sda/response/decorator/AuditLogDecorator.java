package com.sda.response.decorator;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.ResponseAction;
import java.time.Instant;

// PATTERN: Decorator (Concrete Decorator 1)
// RATIONALE: AuditLogDecorator adds logging before and after action execution.
//            Every action is recorded for compliance and forensic analysis.
public class AuditLogDecorator extends ResponseActionDecorator {
    
    public AuditLogDecorator(ResponseAction wrappedAction) {
        super(wrappedAction);
    }
    
    @Override
    public ActionOutcome execute(ResponseContext context) {
        // PRE-ACTION AUDIT
        String auditId = java.util.UUID.randomUUID().toString();
        System.out.println("\n  📝 [AUDIT] Action started: " + getType());
        System.out.println  ("  📝 [AUDIT] ID: " + auditId);
        System.out.println  ("  📝 [AUDIT] Time: " + Instant.now());
        System.out.println  ("  📝 [AUDIT] Context: Incident " + context.getIncidentId());
        
        long startTime = System.currentTimeMillis();
        
        // Execute the wrapped action
        ActionOutcome outcome = wrappedAction.execute(context);
        
        // POST-ACTION AUDIT
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("  📝 [AUDIT] Action completed in " + duration + "ms");
        System.out.println("  📝 [AUDIT] Success: " + outcome.isSuccess());
        System.out.println("  📝 [AUDIT] Message: " + outcome.getMessage());
        
        // In real system: save to audit database
        // auditRepository.save(new AuditRecord(auditId, getType(), outcome, context));
        
        return outcome;
    }
}