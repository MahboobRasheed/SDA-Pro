package com.sda.response.facade;

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

// PATTERN: Facade
// RATIONALE: IncidentResponseFacade provides a simple interface to the complex
//            response execution subsystem, hiding strategy selection, action
//            execution, and outcome tracking from the client.
public class IncidentResponseFacade {
    
    private ResponseStrategySelector strategySelector;
    private List<ActionOutcome> executionHistory;
    
    public IncidentResponseFacade() {
        this.strategySelector = new ResponseStrategySelector();
        this.executionHistory = new ArrayList<>();
    }
    
    // Main facade method - simplifies incident response
    public ResponsePlan assessAndRespond(ResponseContext context) {
        System.out.println("\n========================================");
        System.out.println("🎯 IncidentResponseFacade: Assessing incident");
        System.out.println("   Incident ID: " + context.getIncidentId());
        System.out.println("   Severity: " + context.getSeverity());
        System.out.println("========================================\n");
        
        // Step 1: Select appropriate strategy based on incident context
        ResponseStrategy strategy = strategySelector.selectStrategy(context);
        System.out.println("📋 Selected Strategy: " + strategy.getName());
        
        // Step 2: Get actions from strategy
        List<ResponseActionType> actionTypes = strategy.determineActions(context);
        System.out.println("📋 Actions to execute: " + actionTypes);
        
        // Step 3: Create response plan
        ResponsePlan plan = new ResponsePlan(context.getIncidentId(), strategy.getName(), actionTypes);
        
        return plan;
    }
    
    // Execute the response plan
    public List<ActionOutcome> executeResponsePlan(ResponsePlan plan, ResponseContext context) {
        System.out.println("\n⚡ Executing Response Plan: " + plan.getPlanId());
        
        for (ResponseActionType actionType : plan.getActions()) {
            // Create appropriate action based on type
            ResponseAction action = createAction(actionType);
            
            // Execute the action
            ActionOutcome outcome = action.execute(context);
            plan.addOutcome(outcome);
            executionHistory.add(outcome);
            
            System.out.println("   " + outcome.getActionType() + " → " + 
                (outcome.isSuccess() ? "✅ SUCCESS" : "❌ FAILED"));
        }
        
        plan.setStatus(plan.isComplete() ? "COMPLETED" : "PARTIAL");
        System.out.println("\n📊 Response Plan " + plan.getStatus() + "\n");
        
        return plan.getOutcomes();
    }
    
    // Helper method to create actions
    private ResponseAction createAction(ResponseActionType type) {
        switch (type) {
            case ISOLATE_ENDPOINT:
                return new IsolateEndpointAction();
            case BLOCK_IP:
                return new BlockIPAction();
            default:
                throw new IllegalArgumentException("Unknown action type: " + type);
        }
    }
    
    // Get execution history for an incident
    public List<ActionOutcome> getExecutionHistory(String incidentId) {
        List<ActionOutcome> history = new ArrayList<>();
        for (ActionOutcome outcome : executionHistory) {
            history.add(outcome);
        }
        return history;
    }
    
    // Simple rollback for last action
    public ActionOutcome rollbackLastAction(String incidentId) {
        if (executionHistory.isEmpty()) {
            return ActionOutcome.failure(null, "No actions to rollback");
        }
        
        ActionOutcome lastOutcome = executionHistory.get(executionHistory.size() - 1);
        System.out.println("🔄 Rolling back: " + lastOutcome.getActionType());
        
        ResponseAction action = createAction(lastOutcome.getActionType());
        return action.rollback(lastOutcome.getActionId());
    }
}