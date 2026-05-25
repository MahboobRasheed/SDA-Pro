// PATTERN: Abstract Factory - Standard Product 3
// RATIONALE: Standard Asset lookup using basic inventory.

package services.enrichment;

public class StandardAssetService implements AssetInventoryService {

    @Override
    public String getAssetOwner(String ipAddress) {
        return "Unknown";
    }

    @Override
    public String getAssetCriticality(String ipAddress) {
        return "LOW";
    }

    @Override
    public boolean isCriticalAsset(String ipAddress) {
        return false;
    }
}