package contracts;   // NOT "shared.contracts"

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CanonicalAlert {
    private UUID id;
    private String sourceType;
    private String severity;
    private String sourceIp;
    private String destinationIp;
    private Instant timestamp;
    private Map<String, Object> enrichmentContext;

    public CanonicalAlert() {
        this.id = UUID.randomUUID();
        this.enrichmentContext = new HashMap<>();
    }

    public UUID getId() { return id; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    public String getSourceType() { return sourceType; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getSeverity() { return severity; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }
    public String getSourceIp() { return sourceIp; }
    public void setDestinationIp(String destinationIp) { this.destinationIp = destinationIp; }
    public String getDestinationIp() { return destinationIp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public Instant getTimestamp() { return timestamp; }
    public void addEnrichment(String key, Object value) { enrichmentContext.put(key, value); }
}