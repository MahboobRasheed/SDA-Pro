package com.sda.response.domain;

import java.util.List;
import java.util.ArrayList;

public class ResponsePlan {
    private String planId;
    private String incidentId;
    private String strategyName;
    private List<ResponseActionType> actions;
    private List<ActionOutcome> outcomes;
    private String status;
    
    public ResponsePlan(String incidentId, String strategyName, List<ResponseActionType> actions) {
        this.planId = java.util.UUID.randomUUID().toString();
        this.incidentId = incidentId;
        this.strategyName = strategyName;
        this.actions = new ArrayList<>(actions);
        this.outcomes = new ArrayList<>();
        this.status = "PENDING";
    }
    
    // Getters and Setters
    public String getPlanId() { return planId; }
    public String getIncidentId() { return incidentId; }
    public String getStrategyName() { return strategyName; }
    public List<ResponseActionType> getActions() { return actions; }
    public List<ActionOutcome> getOutcomes() { return outcomes; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
    public void addOutcome(ActionOutcome outcome) { outcomes.add(outcome); }
    
    public boolean isComplete() {
        return outcomes.size() >= actions.size();
    }
}