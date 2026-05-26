// PATTERN: Factory Method - Concrete Product
// RATIONALE: SplunkNormalizer converts Splunk format
// into our canonical alert format.

package services.normalizer;

import shared.contracts.CanonicalAlert;
import java.time.Instant;

public class SplunkNormalizer implements AlertNormalizer {

    @Override
    public CanonicalAlert normalize(String rawPayload) {
        // In real system this parses actual Splunk JSON
        // For now we create a sample normalized alert
        CanonicalAlert alert = new CanonicalAlert();
        alert.setSourceType("SPLUNK");
        alert.setSeverity(extractSeverity(rawPayload));
        alert.setSourceIp(extractSourceIp(rawPayload));
        alert.setRawPayload(rawPayload);
        alert.setTimestamp(Instant.now());
        return alert;
    }

    @Override
    public boolean supports(String sourceType) {
        return "SPLUNK".equalsIgnoreCase(sourceType);
    }

    private String extractSeverity(String payload) {
        if (payload.contains("critical")) return "CRITICAL";
        if (payload.contains("high")) return "HIGH";
        if (payload.contains("medium")) return "MEDIUM";
        return "LOW";
    }

    private String extractSourceIp(String payload) {
        // Simple extraction - real implementation parses JSON
        return "192.168.1.100";
    }
}