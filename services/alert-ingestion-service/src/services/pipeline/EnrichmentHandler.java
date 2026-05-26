// PATTERN: Chain of Responsibility - Abstract Handler
// RATIONALE: Each enrichment step is a separate handler.
// Handlers are chained together. Each one processes the alert
// and passes it to the next handler automatically.

package services.pipeline;

import domain.alert.AlertComponent;

public abstract class EnrichmentHandler {

    // Next handler in the chain
    private EnrichmentHandler nextHandler;

    // Set the next handler and return it (allows chaining)
    public EnrichmentHandler setNext(EnrichmentHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    // Main handle method - process then pass to next
    public EnrichmentResult handle(AlertComponent alert) {

        // Do this handler's work
        EnrichmentResult result = doEnrich(alert);

        // If duplicate found - stop the chain
        if (result == EnrichmentResult.DUPLICATE) {
            System.out.println("DUPLICATE detected - stopping pipeline");
            return result;
        }

        // Pass to next handler if exists
        if (nextHandler != null) {
            return nextHandler.handle(alert);
        }

        return EnrichmentResult.COMPLETE;
    }

    // Each handler must implement this
    protected abstract EnrichmentResult doEnrich(AlertComponent alert);
}