// PATTERN: Abstract Factory - Standard Product 1
// RATIONALE: Standard GeoLocation with basic accuracy.

package services.enrichment;

public class StandardGeoService implements GeoLocationService {

    @Override
    public String lookupLocation(String ipAddress) {
        // Simulates free IP-API basic lookup
        if (ipAddress.startsWith("172.16")) return "Russia";
        if (ipAddress.startsWith("192.168")) return "Internal";
        return "Unknown";
    }

    @Override
    public String lookupCountry(String ipAddress) {
        if (ipAddress.startsWith("172.16")) return "Russia";
        return "Unknown";
    }

    @Override
    public boolean isHighRiskCountry(String ipAddress) {
        return lookupCountry(ipAddress).equals("Russia");
    }
}