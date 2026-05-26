package event_bus.subscribers; 
 
import shared.events.DomainEvent; 
 
public interface Observer { 
    void update(DomainEvent event); 
    String getObserverId(); 
} 
