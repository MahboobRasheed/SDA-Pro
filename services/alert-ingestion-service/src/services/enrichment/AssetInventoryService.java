// PATTERN: Abstract Factory - Product Interface 3
// RATIONALE: Common interface for all Asset providers.
// Premium uses full CMDB, Standard uses basic inventory.

package services.enrichment;

public interface AssetInventoryService {
    String getAssetOwner(String ipAddress);
    String getAssetCriticality(String ipAddress);
    boolean isCriticalAsset(String ipAddress);
}