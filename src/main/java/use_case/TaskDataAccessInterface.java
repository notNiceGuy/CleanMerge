package use_case;

import entity.Task;

import java.util.List;

public interface TaskDataAccessInterface {
    void saveTask(Task task);
    Task getTask(String id);
    boolean deleteTask(String id);
    List<Task> getAllTasks();
}
