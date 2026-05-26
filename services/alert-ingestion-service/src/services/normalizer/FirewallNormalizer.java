// PATTERN: Factory Method - Concrete Product
// RATIONALE: FirewallNormalizer converts Firewall syslog
// format into our canonical alert format.

package services.normalizer;

import shared.contracts.CanonicalAlert;
import java.time.Instant;

public class FirewallNormalizer implements AlertNormalizer {

    @Override
    public CanonicalAlert normalize(String rawPayload) {
        CanonicalAlert alert = new CanonicalAlert();
        alert.setSourceType("FIREWALL");
        alert.setSeverity("HIGH");
        alert.setSourceIp(extractSourceIp(rawPayload));
        alert.setRawPayload(rawPayload);
        alert.setTimestamp(Instant.now());
        return alert;
    }

    @Override
    public boolean supports(String sourceType) {
        return "FIREWALL".equalsIgnoreCase(sourceType);
    }

    private String extractSourceIp(String payload) {
        return "172.16.0.1";
    }
}