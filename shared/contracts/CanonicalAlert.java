// This is the shared contract used by ALL services
package shared.contracts;

import java.time.Instant;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

public class CanonicalAlert {

    private UUID id;
    private String sourceType;    // SPLUNK, CROWDSTRIKE, FIREWALL
    private String severity;      // LOW, MEDIUM, HIGH, CRITICAL
    private String sourceIp;
    private String destinationIp;
    private Instant timestamp;
    private String rawPayload;
    private Map<String, Object> normalizedFields;

    // Constructor
    public CanonicalAlert() {
        this.id = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.normalizedFields = new HashMap<>();
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getDestinationIp() { return destinationIp; }
    public void setDestinationIp(String destinationIp) { this.destinationIp = destinationIp; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getRawPayload() { return rawPayload; }
    public void setRawPayload(String rawPayload) { this.rawPayload = rawPayload; }

    public Map<String, Object> getNormalizedFields() { return normalizedFields; }
    public void addContext(String key, Object value) {
        this.normalizedFields.put(key, value);
    }

    @Override
    public String toString() {
        return "CanonicalAlert{id=" + id +
               ", sourceType=" + sourceType +
               ", severity=" + severity +
               ", sourceIp=" + sourceIp + "}";
    }
}