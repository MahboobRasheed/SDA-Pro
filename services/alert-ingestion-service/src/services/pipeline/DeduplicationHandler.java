// PATTERN: Chain of Responsibility - Handler 1
// RATIONALE: First handler checks if alert is duplicate.
// If duplicate, chain stops here. Otherwise passes forward.

package services.pipeline;

import domain.alert.AlertComponent;
import java.util.HashSet;
import java.util.Set;

public class DeduplicationHandler extends EnrichmentHandler {

    // Simple in-memory cache of seen alerts
    private Set<String> seenAlerts = new HashSet<>();

    @Override
    protected EnrichmentResult doEnrich(AlertComponent alert) {
        System.out.println("Handler 1: Checking for duplicates...");

        // Create a unique key for this alert
        String key = alert.getSourceIp() + "_" +
                     alert.getSeverity() + "_" +
                     alert.getTimestamp().toString().substring(0, 16);

        if (seenAlerts.contains(key)) {
            System.out.println("DUPLICATE found: " + key);
            return EnrichmentResult.DUPLICATE;
        }

        seenAlerts.add(key);
        System.out.println("Not duplicate - passing to next handler");
        return EnrichmentResult.PASS;
    }
}