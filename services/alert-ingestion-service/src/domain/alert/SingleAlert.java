// PATTERN: Composite - Leaf Node
// RATIONALE: SingleAlert is the leaf in the composite tree.
// It represents one individual security alert from any source.

package domain.alert;

import shared.contracts.CanonicalAlert;
import java.util.ArrayList;
import java.util.List;

public class SingleAlert extends AbstractAlert {

    private String rawPayload;
    private CanonicalAlert normalizedData;
    private String alertType;  // INTRUSION, MALWARE, PHISHING, etc.

    // Constructor
    public SingleAlert(CanonicalAlert canonicalAlert) {
        super();
        this.normalizedData = canonicalAlert;
        this.sourceIp = canonicalAlert.getSourceIp();
        this.severity = canonicalAlert.getSeverity();
        this.rawPayload = canonicalAlert.getRawPayload();
    }

    // Leaf node - cannot have children
    @Override
    public void add(AlertComponent component) {
        throw new UnsupportedOperationException(
            "SingleAlert is a leaf - cannot add children"
        );
    }

    @Override
    public void remove(AlertComponent component) {
        throw new UnsupportedOperationException(
            "SingleAlert is a leaf - cannot remove children"
        );
    }

    @Override
    public List<AlertComponent> getChildren() {
        return new ArrayList<>(); // leaf has no children
    }

    @Override
    public boolean isComposite() {
        return false; // this is a leaf node
    }

    // Getters
    public CanonicalAlert getNormalizedData() { return normalizedData; }
    public String getRawPayload() { return rawPayload; }
    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    @Override
    public String toString() {
        return "SingleAlert{id=" + id +
               ", severity=" + severity +
               ", sourceIp=" + sourceIp +
               ", type=" + alertType + "}";
    }
}