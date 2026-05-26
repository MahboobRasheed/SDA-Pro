// PATTERN: Composite
// RATIONALE: AbstractAlert provides common fields shared by
// both SingleAlert and AlertCampaign.

package domain.alert;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractAlert implements AlertComponent {

    protected UUID id;
    protected String severity;
    protected Instant timestamp;
    protected String sourceIp;
    protected Map<String, Object> context;

    // Constructor
    public AbstractAlert() {
        this.id = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.context = new HashMap<>();
    }

    // Getters
    @Override
    public UUID getId() { return id; }

    @Override
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    @Override
    public Instant getTimestamp() { return timestamp; }

    @Override
    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    // Context methods for enrichment
    @Override
    public void addContext(String key, Object value) {
        this.context.put(key, value);
    }

    @Override
    public Object getContext(String key) {
        return this.context.get(key);
    }
}