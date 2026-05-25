// PATTERN: Abstract Factory - Product Interface 1
// RATIONALE: Common interface for all GeoLocation providers.
// Premium uses MaxMind, Standard uses free IP-API.

package services.enrichment;

public interface GeoLocationService {
    String lookupLocation(String ipAddress);
    String lookupCountry(String ipAddress);
    boolean isHighRiskCountry(String ipAddress);
}