// PATTERN: Chain of Responsibility - Pipeline Assembler
// RATIONALE: EnrichmentPipeline builds and runs the full
// chain of handlers in the correct order.

package services.pipeline;

import domain.alert.AlertComponent;

public class EnrichmentPipeline {

    private EnrichmentHandler firstHandler;

    // Build the chain - order matters!
    public EnrichmentPipeline() {
        // Create all handlers
        DeduplicationHandler dedup = new DeduplicationHandler();
        GeoIPHandler geoIP = new GeoIPHandler();
        ThreatIntelHandler threatIntel = new ThreatIntelHandler();
        ClassificationHandler classification = new ClassificationHandler();

        // Chain them together: dedup → geoIP → threatIntel → classification
        dedup.setNext(geoIP)
             .setNext(threatIntel)
             .setNext(classification);

        // First handler starts the chain
        this.firstHandler = dedup;

        System.out.println("Pipeline assembled: Dedup → GeoIP → ThreatIntel → Classification");
    }

    // Run the alert through the entire pipeline
    public EnrichmentResult process(AlertComponent alert) {
        System.out.println("\n=== Starting Enrichment Pipeline ===");
        System.out.println("Alert: " + alert.getSourceIp() +
                          " | Severity: " + alert.getSeverity());
        EnrichmentResult result = firstHandler.handle(alert);
        System.out.println("Pipeline result: " + result);
        System.out.println("=== Pipeline Complete ===\n");
        return result;
    }
}