// PATTERN: State - Concrete State 1
// RATIONALE: NewState is the initial state of every incident.
// Only triage and escalate are allowed here.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public class NewState implements IncidentState {

    @Override
    public void beginTriage(Incident incident, String analystId) {
        System.out.println("STATE: NEW → UNDER_TRIAGE");
        System.out.println("Analyst " + analystId + " is now triaging incident");
        incident.setAssignedAnalyst(analystId);
        incident.setState(new UnderTriageState()); // transition
    }

    @Override
    public void initiateContainment(Incident incident) {
        throw new IllegalStateException(
            "Cannot contain: incident must be triaged first!"
        );
    }

    @Override
    public void beginEradication(Incident incident) {
        throw new IllegalStateException(
            "Cannot eradicate: incident must be triaged first!"
        );
    }

    @Override
    public void beginRecovery(Incident incident) {
        throw new IllegalStateException(
            "Cannot recover: incident must be triaged first!"
        );
    }

    @Override
    public void close(Incident incident) {
        throw new IllegalStateException(
            "Cannot close: incident must go through full lifecycle!"
        );
    }

    @Override
    public void escalate(Incident incident, String reason) {
        System.out.println("Escalating new incident. Reason: " + reason);
        incident.addNote("ESCALATED: " + reason);
    }

    @Override
    public String getName() { return "NEW"; }

    @Override
    public List<String> getAllowedActions() {
        return List.of("BEGIN_TRIAGE", "ESCALATE");
    }
}