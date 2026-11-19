package use_case;

import entity.Task;

import java.util.List;

public interface TaskDataAccessInterface {
    // Adds the task, or replace the stored task that has the same id
    void saveTask(Task task);

    // Return the task with id, or null if it doens't have one
    Task findTaskByID(String id);

    // Remove the task with id, return True if a task was actually removed
    boolean deleteTaskByID(String id);

    // Return every task stored, in the order they are added
    List<Task> findAllTasks();

}
