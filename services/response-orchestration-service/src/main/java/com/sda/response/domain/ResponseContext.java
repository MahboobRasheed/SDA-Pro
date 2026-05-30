package com.sda.response.domain;

public class ResponseContext {
    private String incidentId;
    private String sourceIp;
    private String affectedEndpoint;
    private String suspectUserId;
    private String severity;
    private boolean isCriticalAsset;
    
    public ResponseContext(String incidentId, String severity) {
        this.incidentId = incidentId;
        this.severity = severity;
    }
    
    // Builder pattern
    public ResponseContext withSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
        return this;
    }
    
    public ResponseContext withAffectedEndpoint(String affectedEndpoint) {
        this.affectedEndpoint = affectedEndpoint;
        return this;
    }
    
    public ResponseContext withSuspectUserId(String suspectUserId) {
        this.suspectUserId = suspectUserId;
        return this;
    }
    
    public ResponseContext withCriticalAsset(boolean isCriticalAsset) {
        this.isCriticalAsset = isCriticalAsset;
        return this;
    }
    
    // Getters
    public String getIncidentId() { return incidentId; }
    public String getSourceIp() { return sourceIp; }
    public String getAffectedEndpoint() { return affectedEndpoint; }
    public String getSuspectUserId() { return suspectUserId; }
    public String getSeverity() { return severity; }
    public boolean isCriticalAsset() { return isCriticalAsset; }
}