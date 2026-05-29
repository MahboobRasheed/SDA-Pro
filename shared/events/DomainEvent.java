package events; 
 
import java.time.Instant; 
import java.util.UUID; 
 
public class DomainEvent { 
    private String eventId; 
    private String eventType; 
    private Object payload; 
    private Instant timestamp; 
    private String sourceService; 
 
    public DomainEvent(String eventType, Object payload, String sourceService) { 
        this.eventId = UUID.randomUUID().toString(); 
        this.eventType = eventType; 
        this.payload = payload; 
        this.timestamp = Instant.now(); 
        this.sourceService = sourceService; 
    } 
 
    public String getEventId() { return eventId; } 
    public String getEventType() { return eventType; } 
    public Object getPayload() { return payload; } 
    public Instant getTimestamp() { return timestamp; } 
    public String getSourceService() { return sourceService; } 
} 
