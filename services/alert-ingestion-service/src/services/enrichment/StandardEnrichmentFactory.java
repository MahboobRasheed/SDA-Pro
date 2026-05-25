// PATTERN: Abstract Factory - Concrete Factory 2
// RATIONALE: StandardEnrichmentFactory creates the full
// family of standard providers together. All standard or none.

package services.enrichment;

public class StandardEnrichmentFactory implements EnrichmentProviderFactory {

    @Override
    public GeoLocationService createGeoProvider() {
        System.out.println("Creating STANDARD GeoLocation provider");
        return new StandardGeoService();
    }

    @Override
    public ThreatIntelService createThreatIntelProvider() {
        System.out.println("Creating STANDARD ThreatIntel provider");
        return new StandardThreatIntelService();
    }

    @Override
    public AssetInventoryService createAssetProvider() {
        System.out.println("Creating STANDARD Asset provider");
        return new StandardAssetService();
    }

    @Override
    public String getFactoryType() { return "STANDARD"; }
}