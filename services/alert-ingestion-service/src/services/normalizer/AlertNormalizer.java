// PATTERN: Factory Method
// RATIONALE: Each alert source has a different format.
// AlertNormalizer defines the common interface that all
// normalizers must follow.

package services.normalizer;

import shared.contracts.CanonicalAlert;

public interface AlertNormalizer {

    // Every normalizer must implement this method
    CanonicalAlert normalize(String rawPayload);

    // Check if this normalizer supports the given source
    boolean supports(String sourceType);
}