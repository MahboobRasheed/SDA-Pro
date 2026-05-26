// PATTERN: Factory Method - Concrete Product
// RATIONALE: CrowdStrikeNormalizer converts CrowdStrike EDR
// format into our canonical alert format.

package services.normalizer;

import shared.contracts.CanonicalAlert;
import java.time.Instant;

public class CrowdStrikeNormalizer implements AlertNormalizer {

    @Override
    public CanonicalAlert normalize(String rawPayload) {
        CanonicalAlert alert = new CanonicalAlert();
        alert.setSourceType("CROWDSTRIKE");
        alert.setSeverity(extractSeverity(rawPayload));
        alert.setSourceIp(extractSourceIp(rawPayload));
        alert.setRawPayload(rawPayload);
        alert.setTimestamp(Instant.now());
        return alert;
    }

    @Override
    public boolean supports(String sourceType) {
        return "CROWDSTRIKE".equalsIgnoreCase(sourceType);
    }

    private String extractSeverity(String payload) {
        if (payload.contains("severity_high")) return "HIGH";
        if (payload.contains("severity_critical")) return "CRITICAL";
        return "MEDIUM";
    }

    private String extractSourceIp(String payload) {
        return "10.0.0.55";
    }
}