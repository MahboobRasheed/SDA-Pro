package com.sda.response.executor;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;

// PATTERN: Decorator (Base Component Interface)
// RATIONALE: ResponseAction defines the common interface that both
//            concrete actions and decorators will implement.
public interface ResponseAction {
    ActionOutcome execute(ResponseContext context);
    ResponseActionType getType();
    boolean isReversible();
    ActionOutcome rollback(String actionId);
}