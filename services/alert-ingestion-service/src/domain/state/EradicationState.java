// PATTERN: State - Concrete State 4
// RATIONALE: EradicationState means the threat is being
// removed from all affected systems.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public class EradicationState implements IncidentState {

    @Override
    public void beginTriage(Incident incident, String analystId) {
        throw new IllegalStateException("Already past triage!");
    }

    @Override
    public void initiateContainment(Incident incident) {
        throw new IllegalStateException("Already past containment!");
    }

    @Override
    public void beginEradication(Incident incident) {
        throw new IllegalStateException("Already in eradication!");
    }

    @Override
    public void beginRecovery(Incident incident) {
        System.out.println("STATE: ERADICATION → RECOVERY");
        System.out.println("Threat eradicated. Starting recovery...");
        incident.setState(new RecoveryState()); // transition
    }

    @Override
    public void close(Incident incident) {
        throw new IllegalStateException(
            "Cannot close: must complete recovery first!"
        );
    }

    @Override
    public void escalate(Incident incident, String reason) {
        System.out.println("Escalating during eradication. Reason: " + reason);
        incident.addNote("ESCALATED DURING ERADICATION: " + reason);
    }

    @Override
    public String getName() { return "ERADICATION"; }

    @Override
    public List<String> getAllowedActions() {
        return List.of("BEGIN_RECOVERY", "ESCALATE");
    }
}