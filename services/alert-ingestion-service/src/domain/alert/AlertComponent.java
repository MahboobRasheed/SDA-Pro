// PATTERN: Composite
// RATIONALE: AlertComponent is the common interface for both SingleAlert
// and AlertCampaign so the enrichment pipeline treats them uniformly.

package domain.alert;

import shared.contracts.CanonicalAlert;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AlertComponent {

    // Every alert must have these
    UUID getId();
    String getSeverity();     // LOW, MEDIUM, HIGH, CRITICAL
    Instant getTimestamp();
    String getSourceIp();

    // Composite methods - for grouping alerts together
    void add(AlertComponent component);
    void remove(AlertComponent component);
    List<AlertComponent> getChildren();

    // Add extra information during enrichment
    void addContext(String key, Object value);
    Object getContext(String key);

    // Check if this is a group or single alert
    boolean isComposite();
}