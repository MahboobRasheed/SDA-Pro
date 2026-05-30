package com.sda.response.decorator;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.ResponseAction;

// PATTERN: Decorator (Abstract Decorator)
// RATIONALE: ResponseActionDecorator wraps a ResponseAction and adds
//            additional behavior (audit, approval, metrics) dynamically
//            without modifying the original action classes.
public abstract class ResponseActionDecorator implements ResponseAction {
    
    protected ResponseAction wrappedAction;
    
    public ResponseActionDecorator(ResponseAction wrappedAction) {
        this.wrappedAction = wrappedAction;
    }
    
    @Override
    public ResponseActionType getType() {
        return wrappedAction.getType();
    }
    
    @Override
    public boolean isReversible() {
        return wrappedAction.isReversible();
    }
    
    @Override
    public ActionOutcome rollback(String actionId) {
        // Decorator can add rollback logging here
        System.out.println("  🔄 [DECORATOR] Rollback initiated for: " + actionId);
        return wrappedAction.rollback(actionId);
    }
    
    @Override
    public abstract ActionOutcome execute(ResponseContext context);
}