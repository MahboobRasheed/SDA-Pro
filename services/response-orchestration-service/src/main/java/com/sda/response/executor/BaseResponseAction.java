package com.sda.response.executor;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;


public abstract class BaseResponseAction implements ResponseAction {
    protected ResponseActionType type;
    protected boolean reversible;
    
    public BaseResponseAction(ResponseActionType type, boolean reversible) {
        this.type = type;
        this.reversible = reversible;
    }
    
    @Override
    public ResponseActionType getType() { return type; }
    
    @Override
    public boolean isReversible() { return reversible; }
    
    @Override
    public ActionOutcome rollback(String actionId) {
        if (!reversible) {
            return ActionOutcome.failure(type, "Action is not reversible");
        }
        // Default implementation - subclasses override for specific rollback logic
        return ActionOutcome.success(type, "Rollback completed for: " + actionId);
    }
}