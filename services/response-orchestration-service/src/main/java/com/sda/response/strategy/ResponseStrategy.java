package com.sda.response.strategy;

import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import java.util.List;

// PATTERN: Strategy (Strategy Interface)
// RATIONALE: Defines the common interface for all response strategies.
//            Each strategy encapsulates a different algorithm for determining
//            which response actions to take based on incident context.
public interface ResponseStrategy {
    
    List<ResponseActionType> determineActions(ResponseContext context);
    
    String getName();
    
    String getDescription();
    
    int getPriority();
}