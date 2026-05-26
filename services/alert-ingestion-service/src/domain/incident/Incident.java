// PATTERN: State - Context Class
// RATIONALE: Incident holds the current state and delegates
// all behavior to that state object. This is the core
// of the State pattern.

package domain.incident;

import domain.alert.AlertComponent;
import domain.state.IncidentState;
import domain.state.NewState;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Incident {

    private UUID id;
    private IncidentState currentState;  // current state object
    private AlertComponent rootAlert;
    private String assignedAnalyst;
    private Instant createdAt;
    private List<String> notes;
    private List<String> stateHistory;

    // Constructor - always starts in NewState
    public Incident(AlertComponent alert) {
        this.id = UUID.randomUUID();
        this.rootAlert = alert;
        this.createdAt = Instant.now();
        this.notes = new ArrayList<>();
        this.stateHistory = new ArrayList<>();
        this.currentState = new NewState(); // always start NEW
        stateHistory.add("NEW");
        System.out.println("Incident created: " + id);
    }

    // State pattern - delegate to current state
    public void beginTriage(String analystId) {
        currentState.beginTriage(this, analystId);
    }

    public void initiateContainment() {
        currentState.initiateContainment(this);
    }

    public void beginEradication() {
        currentState.beginEradication(this);
    }

    public void beginRecovery() {
        currentState.beginRecovery(this);
    }

    public void close() {
        currentState.close(this);
    }

    public void escalate(String reason) {
        currentState.escalate(this, reason);
    }

    // Called by state objects to transition
    public void setState(IncidentState newState) {
        System.out.println("Transitioning: " +
            currentState.getName() + " → " + newState.getName());
        this.currentState = newState;
        stateHistory.add(newState.getName());
    }

    // Helper methods
    public void addNote(String note) {
        notes.add(Instant.now() + ": " + note);
    }

    // Getters
    public UUID getId() { return id; }
    public String getCurrentStateName() { return currentState.getName(); }
    public List<String> getAllowedActions() { return currentState.getAllowedActions(); }
    public AlertComponent getRootAlert() { return rootAlert; }
    public String getAssignedAnalyst() { return assignedAnalyst; }
    public void setAssignedAnalyst(String analystId) { this.assignedAnalyst = analystId; }
    public List<String> getNotes() { return notes; }
    public List<String> getStateHistory() { return stateHistory; }
    public Instant getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Incident{id=" + id +
               ", state=" + currentState.getName() +
               ", analyst=" + assignedAnalyst +
               ", history=" + stateHistory + "}";
    }
}