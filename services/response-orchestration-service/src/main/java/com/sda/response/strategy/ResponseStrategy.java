package com.sda.response.strategy;

import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import java.util.List;

public interface ResponseStrategy {
    List<ResponseActionType> determineActions(ResponseContext context);
    String getName();
    String getDescription();
    int getPriority();
}