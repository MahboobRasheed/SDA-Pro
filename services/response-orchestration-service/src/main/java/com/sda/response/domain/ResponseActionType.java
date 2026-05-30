package com.sda.response.domain;

public enum ResponseActionType {
    ISOLATE_ENDPOINT,
    BLOCK_IP,
    DISABLE_USER,
    QUARANTINE_FILE,
    ESCALATE_TO_TIER3,
    SEND_NOTIFICATION
}