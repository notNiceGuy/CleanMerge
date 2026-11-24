package use_case;

import entity.Event;

import java.util.List;

public interface EventDataAccessInterface {
    void saveEvent(Event event);
    Event getEvent(String id);
    boolean deleteEvent(String id);
    List<Event> getAllEvents();
    boolean deleteAllEvents();
}
