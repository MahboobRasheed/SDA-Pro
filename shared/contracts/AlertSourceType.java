package contracts;   // NOT "shared.contracts"

public enum AlertSourceType {
    SPLUNK,
    CROWDSTRIKE,
    FIREWALL,
    CLOUD_SIEM,
    CUSTOM_FEED
}