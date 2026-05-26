// PATTERN: State - State Interface
// RATIONALE: IncidentState defines what actions are allowed
// in each lifecycle phase. Each state has different behavior.

package domain.state;

import domain.incident.Incident;
import java.util.List;

public interface IncidentState {

    // Actions that can be called on an incident
    void beginTriage(Incident incident, String analystId);
    void initiateContainment(Incident incident);
    void beginEradication(Incident incident);
    void beginRecovery(Incident incident);
    void close(Incident incident);
    void escalate(Incident incident, String reason);

    // Get info about this state
    String getName();
    List<String> getAllowedActions();
}