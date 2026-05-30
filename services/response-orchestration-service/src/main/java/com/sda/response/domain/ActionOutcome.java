package com.sda.response.domain;

public class ActionOutcome {
    private String actionId;
    private ResponseActionType actionType;
    private boolean success;
    private String message;
    private String timestamp;
    private boolean reversible;
    
    public ActionOutcome(ResponseActionType actionType, boolean success, String message) {
        this.actionId = java.util.UUID.randomUUID().toString();
        this.actionType = actionType;
        this.success = success;
        this.message = message;
        this.timestamp = java.time.Instant.now().toString();
        this.reversible = false;
    }
    
    // Getters
    public String getActionId() { return actionId; }
    public ResponseActionType getActionType() { return actionType; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
    public boolean isReversible() { return reversible; }
    
    public void setReversible(boolean reversible) { this.reversible = reversible; }
    
    public static ActionOutcome success(ResponseActionType type, String message) {
        return new ActionOutcome(type, true, message);
    }
    
    public static ActionOutcome failure(ResponseActionType type, String message) {
        return new ActionOutcome(type, false, message);
    }
}