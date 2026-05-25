// PATTERN: State - Concrete State 5
// RATIONALE: RecoveryState means systems are being restored
// to normal operation after the threat is removed.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public class RecoveryState implements IncidentState {

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
        throw new IllegalStateException("Already past eradication!");
    }

    @Override
    public void beginRecovery(Incident incident) {
        throw new IllegalStateException("Already in recovery!");
    }

    @Override
    public void close(Incident incident) {
        System.out.println("STATE: RECOVERY → CLOSED");
        System.out.println("Systems recovered. Closing incident.");
        incident.setState(new ClosedState()); // transition
    }

    @Override
    public void escalate(Incident incident, String reason) {
        System.out.println("Escalating during recovery. Reason: " + reason);
        incident.addNote("ESCALATED DURING RECOVERY: " + reason);
    }

    @Override
    public String getName() { return "RECOVERY"; }

    @Override
    public List<String> getAllowedActions() {
        return List.of("CLOSE", "ESCALATE");
    }
}