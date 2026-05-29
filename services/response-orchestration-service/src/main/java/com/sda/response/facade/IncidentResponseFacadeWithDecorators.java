package com.sda.response.facade;

import com.sda.response.decorator.ApprovalGateDecorator;
import com.sda.response.decorator.AuditLogDecorator;
import com.sda.response.decorator.MetricsDecorator;
import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import com.sda.response.domain.ResponsePlan;
import com.sda.response.executor.ResponseAction;
import com.sda.response.executor.concrete.BlockIPAction;
import com.sda.response.executor.concrete.IsolateEndpointAction;
import com.sda.response.strategy.ResponseStrategy;
import com.sda.response.strategy.ResponseStrategySelector;
import java.util.ArrayList;
import java.util.List;

// Enhanced Facade with Decorator Pattern support
public class IncidentResponseFacadeWithDecorators {
    
    private ResponseStrategySelector strategySelector;
    private List<ActionOutcome> executionHistory;
    
    // Flag to enable/disable decorators
    private boolean enableAudit = true;
    private boolean enableApproval = true;
    private boolean enableMetrics = true;
    
    public IncidentResponseFacadeWithDecorators() {
        this.strategySelector = new ResponseStrategySelector();
        this.executionHistory = new ArrayList<>();
    }
    
    public ResponsePlan assessAndRespond(ResponseContext context) {
        System.out.println("\n========================================");
        System.out.println("🎯 IncidentResponseFacade: Assessing incident");
        System.out.println("   Decorators: Audit=" + enableAudit + 
                          ", Approval=" + enableApproval + 
                          ", Metrics=" + enableMetrics);
        System.out.println("========================================\n");
        
        ResponseStrategy strategy = strategySelector.selectStrategy(context);
        System.out.println("📋 Selected Strategy: " + strategy.getName());
        
        List<ResponseActionType> actionTypes = strategy.determineActions(context);
        System.out.println("📋 Actions to execute: " + actionTypes);
        
        ResponsePlan plan = new ResponsePlan(context.getIncidentId(), strategy.getName(), actionTypes);
        
        return plan;
    }
    
    public List<ActionOutcome> executeResponsePlan(ResponsePlan plan, ResponseContext context) {
        System.out.println("\n⚡ Executing Response Plan with Decorators: " + plan.getPlanId());
        
        for (ResponseActionType actionType : plan.getActions()) {
            // Create base action
            ResponseAction baseAction = createBaseAction(actionType);
            
            // Apply decorators in chain (order matters!)
            ResponseAction decoratedAction = applyDecorators(baseAction);
            
            // Execute the decorated action
            ActionOutcome outcome = decoratedAction.execute(context);
            plan.addOutcome(outcome);
            executionHistory.add(outcome);
            
            System.out.println("   " + outcome.getActionType() + " → " + 
                (outcome.isSuccess() ? "✅ SUCCESS" : "❌ FAILED"));
        }
        
        plan.setStatus(plan.isComplete() ? "COMPLETED" : "PARTIAL");
        System.out.println("\n📊 Response Plan " + plan.getStatus() + "\n");
        
        return plan.getOutcomes();
    }
    
    private ResponseAction createBaseAction(ResponseActionType type) {
        switch (type) {
            case ISOLATE_ENDPOINT:
                return new IsolateEndpointAction();
            case BLOCK_IP:
                return new BlockIPAction();
            default:
                throw new IllegalArgumentException("Unknown action type: " + type);
        }
    }
    
    private ResponseAction applyDecorators(ResponseAction action) {
        ResponseAction decorated = action;
        
        // Apply decorators from inside-out (last applied executes first)
        // Order: Base → Audit → Approval → Metrics (Metrics wraps everything)
        
        if (enableAudit) {
            decorated = new AuditLogDecorator(decorated);
        }
        
        if (enableApproval) {
            decorated = new ApprovalGateDecorator(decorated);
        }
        
        if (enableMetrics) {
            decorated = new MetricsDecorator(decorated);
        }
        
        return decorated;
    }
    
    public void setEnableAudit(boolean enable) { this.enableAudit = enable; }
    public void setEnableApproval(boolean enable) { this.enableApproval = enable; }
    public void setEnableMetrics(boolean enable) { this.enableMetrics = enable; }
    
    public static void main(String[] args) {
        System.out.println("\n========== FACADE + DECORATOR INTEGRATION DEMO ==========\n");
        
        IncidentResponseFacadeWithDecorators facade = new IncidentResponseFacadeWithDecorators();
        
        // Test with all decorators enabled
        ResponseContext context = new ResponseContext("INC-001", "CRITICAL")
            .withSourceIp("185.130.5.253")
            .withAffectedEndpoint("finance-server-01")
            .withCriticalAsset(true);
        
        ResponsePlan plan = facade.assessAndRespond(context);
        facade.executeResponsePlan(plan, context);
        
        // Print metrics
        MetricsDecorator.printMetricsReport();
        
        // Test without approval decorator
        System.out.println("\n--- TEST 2: Disabling Approval Decorator ---");
        facade.setEnableApproval(false);
        
        ResponseContext context2 = new ResponseContext("INC-002", "HIGH")
            .withSourceIp("45.33.22.11");
        
        ResponsePlan plan2 = facade.assessAndRespond(context2);
        facade.executeResponsePlan(plan2, context2);
        
        MetricsDecorator.printMetricsReport();
        
        System.out.println("========== INTEGRATION DEMO COMPLETE ==========");
    }
}