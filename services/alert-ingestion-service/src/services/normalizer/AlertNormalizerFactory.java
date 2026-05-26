// PATTERN: Factory Method
// RATIONALE: AlertNormalizerFactory decides which normalizer
// to create based on the alert source type.
// New sources can be added without changing existing code.

package services.normalizer;

import java.util.HashMap;
import java.util.Map;

public class AlertNormalizerFactory {

    // Registry of all available normalizers
    private static Map<String, AlertNormalizer> registry = new HashMap<>();

    // Register normalizers at startup
    static {
        registry.put("SPLUNK", new SplunkNormalizer());
        registry.put("CROWDSTRIKE", new CrowdStrikeNormalizer());
        registry.put("FIREWALL", new FirewallNormalizer());
    }

    // Factory method - creates the right normalizer
    public static AlertNormalizer createNormalizer(String sourceType) {
        AlertNormalizer normalizer = registry.get(sourceType.toUpperCase());

        if (normalizer == null) {
            throw new IllegalArgumentException(
                "No normalizer found for source: " + sourceType
            );
        }
        return normalizer;
    }

    // Register a new normalizer
    public static void register(String sourceType, AlertNormalizer normalizer) {
        registry.put(sourceType.toUpperCase(), normalizer);
    }
}