package com.sda.response.decorator;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.ResponseAction;
import com.sda.response.executor.concrete.BlockIPAction;
import com.sda.response.executor.concrete.IsolateEndpointAction;

// PATTERN: Decorator (Chain Demo)
// RATIONALE: Demonstrates how decorators can be stacked to add
//            multiple behaviors (audit + approval + metrics) to an action.
public class DecoratorChainDemo {
    
    public static void main(String[] args) {
        System.out.println("\n========== DECORATOR PATTERN DEMO ==========\n");
        
        // Create a simple action
        ResponseAction blockIPAction = new BlockIPAction();
        ResponseAction isolateAction = new IsolateEndpointAction();
        
        // Demo 1: Single decorator
        System.out.println("--- DEMO 1: Audit Only ---");
        ResponseAction auditedAction = new AuditLogDecorator(blockIPAction);
        ResponseContext context = new ResponseContext("INC-001", "HIGH")
            .withSourceIp("185.130.5.253");
        auditedAction.execute(context);
        
        // Demo 2: Multiple decorators stacked
        System.out.println("\n--- DEMO 2: Audit + Approval + Metrics (Stacked) ---");
        
        // Stack decorators: Start with base action, then wrap with decorators
        ResponseAction baseAction = new BlockIPAction();
        ResponseAction withAudit = new AuditLogDecorator(baseAction);
        ResponseAction withApproval = new ApprovalGateDecorator(withAudit);
        ResponseAction withMetrics = new MetricsDecorator(withApproval);
        
        // Execute the fully decorated action
        ResponseContext criticalContext = new ResponseContext("INC-002", "CRITICAL")
            .withSourceIp("45.33.22.11")
            .withCriticalAsset(true);
        
        ActionOutcome outcome = withMetrics.execute(criticalContext);
        System.out.println("\n  Final Outcome: " + outcome.isSuccess() + " - " + outcome.getMessage());
        
        // Demo 3: Different decorator order for isolate action
        System.out.println("\n--- DEMO 3: Isolate Action with Approval Gate (Critical Action) ---");
        
        ResponseAction isolateBase = new IsolateEndpointAction();
        ResponseAction isolateWithApproval = new ApprovalGateDecorator(isolateBase);
        ResponseAction isolateFull = new AuditLogDecorator(isolateWithApproval);
        
        ResponseContext isolateContext = new ResponseContext("INC-003", "MEDIUM")
            .withAffectedEndpoint("workstation-01");
        
        isolateFull.execute(isolateContext);
        
        // Print metrics report
        MetricsDecorator.printMetricsReport();
        
        System.out.println("========== DECORATOR DEMO COMPLETE ==========\n");
    }
}