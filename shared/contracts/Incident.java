package shared.contracts; 
 
import java.time.Instant; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.UUID; 
 
public class Incident { 
    private UUID id; 
    private String title; 
    private String severity; 
    private String currentState; 
    private Instant createdAt; 
    private List<String> responseHistory; 
 
    public Incident() { 
        this.id = UUID.randomUUID(); 
        this.createdAt = Instant.now(); 
        this.currentState = "NEW"; 
        this.responseHistory = new ArrayList<>(); 
    } 
 
    public UUID getId() { return id; } 
    public void setTitle(String title) { this.title = title; } 
    public String getTitle() { return title; } 
    public void setSeverity(String severity) { this.severity = severity; } 
    public String getSeverity() { return severity; } 
    public void setCurrentState(String currentState) { this.currentState = currentState; } 
    public String getCurrentState() { return currentState; } 
    public void addResponseHistory(String action) { responseHistory.add(action); } 
} 
