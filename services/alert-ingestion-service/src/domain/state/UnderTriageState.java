// PATTERN: State - Concrete State 2
// RATIONALE: UnderTriageState means an analyst is actively
// investigating. Containment can now be initiated.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public class UnderTriageState implements IncidentState {

    @Override
    public void beginTriage(Incident incident, String analystId) {
        throw new IllegalStateException(
            "Already under triage!"
        );
    }

    @Override
    public void initiateContainment(Incident incident) {
        System.out.println("STATE: UNDER_TRIAGE → CONTAINMENT");
        System.out.println("Starting containment actions...");
        incident.setState(new ContainmentState()); // transition
    }

    @Override
    public void beginEradication(Incident incident) {
        throw new IllegalStateException(
            "Cannot eradicate: must contain threat first!"
        );
    }

    @Override
    public void beginRecovery(Incident incident) {
        throw new IllegalStateException(
            "Cannot recover: must contain threat first!"
        );
    }

    @Override
    public void close(Incident incident) {
        throw new IllegalStateException(
            "Cannot close: must complete full lifecycle!"
        );
    }

    @Override
    public void escalate(Incident incident, String reason) {
        System.out.println("Escalating during triage. Reason: " + reason);
        incident.addNote("ESCALATED DURING TRIAGE: " + reason);
    }

    @Override
    public String getName() { return "UNDER_TRIAGE"; }

    @Override
    public List<String> getAllowedActions() {
        return List.of("INITIATE_CONTAINMENT", "ESCALATE");
    }
}