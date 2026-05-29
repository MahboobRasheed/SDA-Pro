package com.sda.response.executor.concrete;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.BaseResponseAction;

public class IsolateEndpointAction extends BaseResponseAction {
    
    public IsolateEndpointAction() {
        super(ResponseActionType.ISOLATE_ENDPOINT, true);
    }
    
    @Override
    public ActionOutcome execute(ResponseContext context) {
        String endpoint = context.getAffectedEndpoint();
        if (endpoint == null) {
            return ActionOutcome.failure(type, "No endpoint specified for isolation");
        }
        
        System.out.println("🔒 Isolating endpoint: " + endpoint);
        // In real system: call network API to isolate endpoint
        return ActionOutcome.success(type, "Endpoint " + endpoint + " isolated successfully");
    }
    
    @Override
    public ActionOutcome rollback(String actionId) {
        System.out.println("🔓 Restoring network access for endpoint");
        return ActionOutcome.success(type, "Rollback: Endpoint access restored");
    }
}