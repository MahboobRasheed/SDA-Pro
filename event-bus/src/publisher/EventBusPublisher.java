package event_bus.publisher; 
 
import shared.events.DomainEvent; 
import shared.events.EventTypes; 
import shared.contracts.Incident; 
import shared.contracts.CanonicalAlert; 
 
import java.util.List; 
import java.util.Map; 
import java.util.concurrent.ConcurrentHashMap; 
import java.util.concurrent.CopyOnWriteArrayList; 
 
public class EventBusPublisher { 
    private static volatile EventBusPublisher instance; 
 
    private EventBusPublisher() { 
        this.observers = new ConcurrentHashMap<>(); 
    } 
 
    public static EventBusPublisher getInstance() { 
        if (instance == null) { 
            synchronized (EventBusPublisher.class) { 
                if (instance == null) instance = new EventBusPublisher(); 
            } 
        } 
        return instance; 
    } 
 
    public void attach(String eventType, Observer observer) { 
        observers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(observer); 
    } 
 
    public void detach(String eventType, Observer observer) { 
        List<Observer> subs = observers.get(eventType); 
        if (subs != null) subs.remove(observer); 
    } 
 
    public void notify(DomainEvent event) { 
        List<Observer> subs = observers.getOrDefault(event.getEventType(), List.of()); 
        subs.forEach(observer -> observer.update(event)); 
    } 
 
    public void publishIncidentCreated(Incident incident) { 
        DomainEvent event = new DomainEvent(EventTypes.INCIDENT_CREATED, incident, "Dashboard"); 
        notify(event); 
    } 
} 
