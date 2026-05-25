// PATTERN: Abstract Factory - Premium Product 3
// RATIONALE: Premium Asset lookup using full CMDB database.

package services.enrichment;

public class PremiumAssetService implements AssetInventoryService {

    @Override
    public String getAssetOwner(String ipAddress) {
        if (ipAddress.startsWith("192.168.1")) return "Finance Department";
        if (ipAddress.startsWith("192.168.2")) return "Engineering Department";
        return "Unknown Department";
    }

    @Override
    public String getAssetCriticality(String ipAddress) {
        if (ipAddress.startsWith("192.168.1")) return "CRITICAL";
        if (ipAddress.startsWith("192.168.2")) return "HIGH";
        return "MEDIUM";
    }

    @Override
    public boolean isCriticalAsset(String ipAddress) {
        return getAssetCriticality(ipAddress).equals("CRITICAL");
    }
}