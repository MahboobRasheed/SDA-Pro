package event_bus.subscribers; 
 
import shared.events.DomainEvent; 
 
public class AuditLogger implements Observer { 
    private final String observerId; 
 
    public AuditLogger() { 
        this.observerId = "AuditLogger-" + System.currentTimeMillis(); 
    } 
 
    @Override 
    public void update(DomainEvent event) { 
        System.out.println("[AUDIT] " + event.getTimestamp() + " | " + event.getEventType() + " | " + event.getSourceService()); 
    } 
 
    @Override 
    public String getObserverId() { return observerId; } 
} 
