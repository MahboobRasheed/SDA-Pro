// PATTERN: Chain of Responsibility - Handler 3
// RATIONALE: Third handler checks the source IP against
// threat intelligence databases to find reputation score.

package services.pipeline;

import domain.alert.AlertComponent;

public class ThreatIntelHandler extends EnrichmentHandler {

    @Override
    protected EnrichmentResult doEnrich(AlertComponent alert) {
        System.out.println("Handler 3: Checking threat intel for " + alert.getSourceIp());

        // In real system this calls VirusTotal or MISP API
        // For now we simulate reputation check
        String reputation = checkReputation(alert.getSourceIp());
        int score = getScore(alert.getSourceIp());

        // Add threat intel context to alert
        alert.addContext("threatReputation", reputation);
        alert.addContext("threatScore", score);

        System.out.println("Threat Intel: " + reputation + " (score: " + score + ")");
        return EnrichmentResult.PASS;
    }

    private String checkReputation(String ip) {
        if (ip.startsWith("172.16")) return "MALICIOUS";
        if (ip.startsWith("10.0")) return "SUSPICIOUS";
        return "CLEAN";
    }

    private int getScore(String ip) {
        if (ip.startsWith("172.16")) return 95;
        if (ip.startsWith("10.0")) return 45;
        return 5;
    }
}