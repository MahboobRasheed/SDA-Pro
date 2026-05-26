// PATTERN: State - Concrete State 3
// RATIONALE: ContainmentState means threat is being contained.
// Eradication is the next allowed step.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public class ContainmentState implements IncidentState {

    @Override
    public void beginTriage(Incident incident, String analystId) {
        throw new IllegalStateException("Already past triage!");
    }

    @Override
    public void initiateContainment(Incident incident) {
        throw new IllegalStateException("Already in containment!");
    }

    @Override
    public void beginEradication(Incident incident) {
        System.out.println("STATE: CONTAINMENT → ERADICATION");
        System.out.println("Threat contained. Starting eradication...");
        incident.setState(new EradicationState()); // transition
    }

    @Override
    public void beginRecovery(Incident incident) {
        throw new IllegalStateException(
            "Cannot recover: must eradicate threat first!"
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
        System.out.println("Escalating during containment. Reason: " + reason);
        incident.addNote("ESCALATED DURING CONTAINMENT: " + reason);
    }

    @Override
    public String getName() { return "CONTAINMENT"; }

    @Override
    public List<String> getAllowedActions() {
        return List.of("BEGIN_ERADICATION", "ESCALATE");
    }
}