package data_access;


import entity.Task;
import use_case.TaskDataAccessInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// this will be the shared storage to store all the entities
public class InMemoryDataAccessObject implements TaskDataAccessInterface { // will implement other DAI later on


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

}
