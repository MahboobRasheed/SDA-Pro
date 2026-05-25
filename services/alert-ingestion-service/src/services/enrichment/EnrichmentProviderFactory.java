// PATTERN: Abstract Factory - Factory Interface
// RATIONALE: EnrichmentProviderFactory creates FAMILIES of
// related enrichment providers together. Premium customers
// get better providers than Standard customers.

package services.enrichment;

public interface EnrichmentProviderFactory {

    // Create a family of related providers
    GeoLocationService createGeoProvider();
    ThreatIntelService createThreatIntelProvider();
    AssetInventoryService createAssetProvider();

    // Factory name for logging
    String getFactoryType();
}