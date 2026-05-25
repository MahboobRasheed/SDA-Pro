// PATTERN: Chain of Responsibility - Handler 4
// RATIONALE: Fourth handler classifies the alert severity
// based on all enrichment data collected by previous handlers.

package services.pipeline;

import domain.alert.AlertComponent;

public class ClassificationHandler extends EnrichmentHandler {

    @Override
    protected EnrichmentResult doEnrich(AlertComponent alert) {
        System.out.println("Handler 4: Classifying alert severity...");

        // Get context added by previous handlers
        String reputation = (String) alert.getContext("threatReputation");
        String country = (String) alert.getContext("country");

        // Classify based on enrichment data
        String classification = classify(alert.getSeverity(), reputation, country);
        alert.addContext("classification", classification);
        alert.addContext("requiresIncident", shouldCreateIncident(classification));

        System.out.println("Classification: " + classification);
        return EnrichmentResult.PASS;
    }

    private String classify(String severity, String reputation, String country) {
        if ("MALICIOUS".equals(reputation) && "CRITICAL".equals(severity)) {
            return "APT_ATTACK";
        }
        if ("MALICIOUS".equals(reputation)) {
            return "MALWARE_INFECTION";
        }
        if ("SUSPICIOUS".equals(reputation)) {
            return "SUSPICIOUS_ACTIVITY";
        }
        return "NORMAL_ALERT";
    }

    private boolean shouldCreateIncident(String classification) {
        return classification.equals("APT_ATTACK") ||
               classification.equals("MALWARE_INFECTION");
    }
}