// PATTERN: Abstract Factory - Premium Product 2
// RATIONALE: Premium ThreatIntel using VirusTotal API.

package services.enrichment;

public class PremiumThreatIntelService implements ThreatIntelService {

    @Override
    public String checkReputation(String indicator) {
        // Simulates VirusTotal premium check
        if (indicator.startsWith("172.16")) return "MALICIOUS";
        if (indicator.startsWith("10.0")) return "SUSPICIOUS";
        return "CLEAN";
    }

    @Override
    public int getThreatScore(String indicator) {
        if (indicator.startsWith("172.16")) return 95;
        if (indicator.startsWith("10.0")) return 45;
        return 2;
    }

    @Override
    public boolean isMalicious(String indicator) {
        return getThreatScore(indicator) > 70;
    }
}