// PATTERN: Abstract Factory - Product Interface 2
// RATIONALE: Common interface for all ThreatIntel providers.
// Premium uses VirusTotal, Standard uses basic blocklist.

package services.enrichment;

public interface ThreatIntelService {
    String checkReputation(String indicator);
    int getThreatScore(String indicator);
    boolean isMalicious(String indicator);
}