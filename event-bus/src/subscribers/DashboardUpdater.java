package event_bus.subscribers; 
 
import shared.events.DomainEvent; 
import shared.events.EventTypes; 
import shared.contracts.Incident; 
import shared.contracts.CanonicalAlert; 
 
import java.util.Map; 
import java.util.concurrent.ConcurrentHashMap; 
 
public class DashboardUpdater implements Observer { 
    private final String observerId; 
 
    public DashboardUpdater() { 
        this.observerId = "DashboardUpdater-" + System.currentTimeMillis(); 
        this.sessions = new ConcurrentHashMap<>(); 
    } 
 
    @Override 
    public void update(DomainEvent event) { 
        String message = null; 
 
        if (EventTypes.INCIDENT_CREATED.equals(event.getEventType())) { 
            Incident incident = (Incident) event.getPayload(); 
            message = "{\"type\":\"INCIDENT_CREATED\",\"incidentId\":\"" + incident.getId() + "\",\"severity\":\"" + incident.getSeverity() + "\"}"; 
        } 
 
        if (message != null) { 
            broadcast(message); 
        } 
    } 
 
    private void broadcast(String message) { 
        System.out.println("Broadcasting: " + message); 
    } 
 
    @Override 
    public String getObserverId() { return observerId; } 
} 
