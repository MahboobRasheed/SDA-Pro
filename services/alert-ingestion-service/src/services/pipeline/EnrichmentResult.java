// PATTERN: Chain of Responsibility - Result enum
// RATIONALE: Each handler returns a result to control
// whether the chain continues or stops.

package services.pipeline;

public enum EnrichmentResult {
    PASS,        // continue to next handler
    COMPLETE,    // all handlers finished successfully
    DUPLICATE,   // alert is duplicate - stop chain
    FAILED       // something went wrong
}