package com.sda.response.executor.concrete;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseActionType;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.BaseResponseAction;

public class BlockIPAction extends BaseResponseAction {
    
    public BlockIPAction() {
        super(ResponseActionType.BLOCK_IP, true);
    }
    
    @Override
    public ActionOutcome execute(ResponseContext context) {
        String ip = context.getSourceIp();
        if (ip == null) {
            return ActionOutcome.failure(type, "No IP specified for blocking");
        }
        
        System.out.println("🚫 Blocking IP: " + ip);
        // In real system: call firewall API to block IP
        return ActionOutcome.success(type, "IP " + ip + " blocked successfully");
    }
    
    @Override
    public ActionOutcome rollback(String actionId) {
        System.out.println("✅ Unblocking IP (rollback)");
        return ActionOutcome.success(type, "Rollback: IP unblocked");
    }
}