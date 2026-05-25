// PATTERN: State - Concrete State 6
// RATIONALE: ClosedState is the final state.
// No further actions are allowed on a closed incident.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public class ClosedState implements IncidentState {

    @Override
    public void beginTriage(Incident incident, String analystId) {
        throw new IllegalStateException("Incident is closed!");
    }

    @Override
    public void initiateContainment(Incident incident) {
        throw new IllegalStateException("Incident is closed!");
    }

    @Override
    public void beginEradication(Incident incident) {
        throw new IllegalStateException("Incident is closed!");
    }

    @Override
    public void beginRecovery(Incident incident) {
        throw new IllegalStateException("Incident is closed!");
    }

    @Override
    public void close(Incident incident) {
        throw new IllegalStateException("Incident is already closed!");
    }

    @Override
    public void escalate(Incident incident, String reason) {
        throw new IllegalStateException(
            "Cannot escalate a closed incident!"
        );
    }

    @Override
    public String getName() { return "CLOSED"; }

    @Override
    public List<String> getAllowedActions() {
        return List.of("READ_ONLY");
    }
}