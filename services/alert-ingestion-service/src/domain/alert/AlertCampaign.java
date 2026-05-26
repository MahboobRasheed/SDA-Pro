// PATTERN: Composite - Composite Node
// RATIONALE: AlertCampaign groups multiple alerts together
// representing a multi-stage attack like APT campaigns.
// Both SingleAlert and AlertCampaign are treated uniformly.

package domain.alert;

import java.util.ArrayList;
import java.util.List;

public class AlertCampaign extends AbstractAlert {

    private String campaignName;
    private String attackPattern;  // e.g. "lateral-movement", "APT29"
    private List<AlertComponent> children;

    // Constructor
    public AlertCampaign(String campaignName, String attackPattern) {
        super();
        this.campaignName = campaignName;
        this.attackPattern = attackPattern;
        this.children = new ArrayList<>();
    }

    // Composite node - CAN have children
    @Override
    public void add(AlertComponent component) {
        children.add(component);
    }

    @Override
    public void remove(AlertComponent component) {
        children.remove(component);
    }

    @Override
    public List<AlertComponent> getChildren() {
        return children;
    }

    @Override
    public boolean isComposite() {
        return true; // this is a composite node
    }

    // Automatically get the highest severity from all children
    @Override
    public String getSeverity() {
        String highest = "LOW";
        for (AlertComponent child : children) {
            if (child.getSeverity().equals("CRITICAL")) return "CRITICAL";
            if (child.getSeverity().equals("HIGH")) highest = "HIGH";
            if (child.getSeverity().equals("MEDIUM") && highest.equals("LOW"))
                highest = "MEDIUM";
        }
        return highest;
    }

    // Getters
    public String getCampaignName() { return campaignName; }
    public String getAttackPattern() { return attackPattern; }
    public int getAlertCount() { return children.size(); }

    @Override
    public String toString() {
        return "AlertCampaign{name=" + campaignName +
               ", pattern=" + attackPattern +
               ", alertCount=" + children.size() +
               ", severity=" + getSeverity() + "}";
    }
}