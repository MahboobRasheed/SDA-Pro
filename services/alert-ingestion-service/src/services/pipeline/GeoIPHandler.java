// PATTERN: Chain of Responsibility - Handler 2
// RATIONALE: Second handler enriches alert with
// geographic location of the source IP address.

package services.pipeline;

import domain.alert.AlertComponent;

public class GeoIPHandler extends EnrichmentHandler {

    @Override
    protected EnrichmentResult doEnrich(AlertComponent alert) {
        System.out.println("Handler 2: Looking up GeoIP for " + alert.getSourceIp());

        // In real system this calls a GeoIP API
        // For now we simulate based on IP range
        String location = simulateGeoLookup(alert.getSourceIp());

        // Add location context to the alert
        alert.addContext("geoLocation", location);
        alert.addContext("country", extractCountry(location));

        System.out.println("GeoIP found: " + location);
        return EnrichmentResult.PASS;
    }

    private String simulateGeoLookup(String ip) {
        if (ip.startsWith("192.168")) return "Internal Network, Local";
        if (ip.startsWith("10.0")) return "Internal Network, DMZ";
        if (ip.startsWith("172.16")) return "Russia, Moscow";
        return "Unknown, External";
    }

    private String extractCountry(String location) {
        return location.split(",")[0].trim();
    }
}