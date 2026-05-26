// PATTERN: Abstract Factory - Concrete Factory 1
// RATIONALE: PremiumEnrichmentFactory creates the full
// family of premium providers together. All premium or none.

package services.enrichment;

public class PremiumEnrichmentFactory implements EnrichmentProviderFactory {

    @Override
    public GeoLocationService createGeoProvider() {
        System.out.println("Creating PREMIUM GeoLocation provider");
        return new PremiumGeoService();
    }

    @Override
    public ThreatIntelService createThreatIntelProvider() {
        System.out.println("Creating PREMIUM ThreatIntel provider");
        return new PremiumThreatIntelService();
    }

    @Override
    public AssetInventoryService createAssetProvider() {
        System.out.println("Creating PREMIUM Asset provider");
        return new PremiumAssetService();
    }

    @Override
    public String getFactoryType() { return "PREMIUM"; }
}