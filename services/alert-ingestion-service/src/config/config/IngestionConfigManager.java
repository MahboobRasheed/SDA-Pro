// PATTERN: Singleton
// RATIONALE: Only one instance of IngestionConfigManager should exist
// in the entire system. It is the single source of truth for all
// alert source configurations.

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngestionConfigManager {

    // Step 1 - private static instance
    private static IngestionConfigManager instance;

    // Configuration data
    private Map<String, Boolean> sourceEnabled;
    private Map<String, String> sourceUrls;
    private List<String> supportedSources;

    // Step 2 - private constructor so nobody can do "new IngestionConfigManager()"
    private IngestionConfigManager() {
        sourceEnabled = new HashMap<>();
        sourceUrls = new HashMap<>();
        supportedSources = new ArrayList<>();

        // Default configuration
        sourceEnabled.put("SPLUNK", true);
        sourceEnabled.put("CROWDSTRIKE", true);
        sourceEnabled.put("FIREWALL", true);
        sourceEnabled.put("CLOUD_SIEM", false);

        sourceUrls.put("SPLUNK", "http://splunk:8089/api");
        sourceUrls.put("CROWDSTRIKE", "http://crowdstrike:8090/api");
        sourceUrls.put("FIREWALL", "http://firewall:514/syslog");

        supportedSources.add("SPLUNK");
        supportedSources.add("CROWDSTRIKE");
        supportedSources.add("FIREWALL");
        supportedSources.add("CLOUD_SIEM");
    }

    // Step 3 - public static method to get the ONE instance
    public static synchronized IngestionConfigManager getInstance() {
        if (instance == null) {
            instance = new IngestionConfigManager();
        }
        return instance;
    }

    // Methods to use the config
    public boolean isSourceEnabled(String sourceType) {
        return sourceEnabled.getOrDefault(sourceType, false);
    }

    public String getSourceUrl(String sourceType) {
        return sourceUrls.getOrDefault(sourceType, "");
    }

    public List<String> getSupportedSources() {
        return Collections.unmodifiableList(new ArrayList<>(supportedSources));
    }

    public void enableSource(String sourceType) {
        sourceEnabled.put(sourceType, true);
    }

    public void disableSource(String sourceType) {
        sourceEnabled.put(sourceType, false);
    }

    @Override
    public String toString() {
        return "IngestionConfigManager{sources=" + supportedSources + "}";
    }
}