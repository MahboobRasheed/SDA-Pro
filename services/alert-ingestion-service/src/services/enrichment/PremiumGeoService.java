// PATTERN: Abstract Factory - Premium Product 1
// RATIONALE: Premium GeoLocation with detailed accuracy.

package services.enrichment;

public class PremiumGeoService implements GeoLocationService {

    @Override
    public String lookupLocation(String ipAddress) {
        // Simulates MaxMind premium lookup
        if (ipAddress.startsWith("172.16")) return "Moscow, Russia, EU";
        if (ipAddress.startsWith("192.168")) return "Internal, Corporate HQ";
        return "New York, USA, NA";
    }

    @Override
    public String lookupCountry(String ipAddress) {
        if (ipAddress.startsWith("172.16")) return "Russia";
        if (ipAddress.startsWith("192.168")) return "Internal";
        return "USA";
    }

    @Override
    public boolean isHighRiskCountry(String ipAddress) {
        String country = lookupCountry(ipAddress);
        return country.equals("Russia") || country.equals("North Korea");
    }
}