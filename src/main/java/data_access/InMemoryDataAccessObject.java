package data_access;


import entity.Task;
import entity.Event;
import use_case.EventDataAccessInterface;
import use_case.TaskDataAccessInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// this will be the shared storage to store all the entities
public class InMemoryDataAccessObject implements TaskDataAccessInterface, EventDataAccessInterface { // will implement other DAI later on


    // Use case 1 DAO
    private final Map<String, Task> tasks = new LinkedHashMap<>(); // LinkedHashMap remembers the order tasks were added

    @Override
    public void saveTask(Task task) {
        tasks.put(task.getId(), task);
    }
    // put() adds a new entry or replace one with the same id (editing)
    // for both adding and editing a task actions and store the task into this storage

    @Override
    public Task getTask(String id) {
        return tasks.get(id);
    }

    @Override
    public boolean deleteTask(String id) {
        return tasks.remove(id) != null;
    } // remove() returns the removed Task, or null if none

    @Override
    public List<Task> getAllTasks() {
        // Return a copy
        return new ArrayList<>(tasks.values());
    }

    // Use case 2 DAO
    private final Map<String, Event> events = new LinkedHashMap<>();

    @Override
    public void saveEvent(Event event) {
        events.put(event.getId(), event);
    }

    @Override
    public Event getEvent(String id) {
        return events.get(id);
    }

    @Override
    public boolean deleteEvent(String id) {
        return events.remove(id) != null;
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    @Override
    public boolean deleteAllEvents() {
        if (events.isEmpty()) {
            return false; // No events to delete
        }
        events.clear();
        return true; // All events deleted successfully
    }
}
