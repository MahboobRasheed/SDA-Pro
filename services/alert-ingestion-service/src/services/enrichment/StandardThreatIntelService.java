// PATTERN: Abstract Factory - Standard Product 2
// RATIONALE: Standard ThreatIntel using basic blocklist.

package services.enrichment;

public class StandardThreatIntelService implements ThreatIntelService {

    @Override
    public String checkReputation(String indicator) {
        // Simulates basic blocklist check
        if (indicator.startsWith("172.16")) return "BLOCKED";
        return "UNKNOWN";
    }

    @Override
    public int getThreatScore(String indicator) {
        if (indicator.startsWith("172.16")) return 80;
        return 0;
    }

    @Override
    public boolean isMalicious(String indicator) {
        return checkReputation(indicator).equals("BLOCKED");
    }
}