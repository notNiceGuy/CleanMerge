package interface_adapters.use_case1_manage_tasks;

// Packages the values into ManageTasksInputData and passes them onto the interactor via ManageTasksInputBoundary

import use_case.use_case1_manage_tasks.ManageTasksInputBoundary;
import use_case.use_case1_manage_tasks.ManageTasksInputData;

public class TaskController {
    private static final String DEFAULT_STATUS = "NO";

    private final ManageTasksInputBoundary interactor;

    public TaskController(ManageTasksInputBoundary interactor) {
        this.interactor = interactor;
    }

    // Default status since the assumption is that if we add a new task the task is not completed
    public void addTask(String title, int estimatedMinutes, String description) {
        interactor.addTask(new ManageTasksInputData(null, title, description, estimatedMinutes, DEFAULT_STATUS));
    }

    public void editTask(String taskId, String title, String description, int estimatedMinutes, String completed) {
        interactor.editTask(new ManageTasksInputData(taskId, title, description, estimatedMinutes, completed));
    }

    public void deleteTask(String taskId) {
        interactor.deleteTask(taskId);
    }
}
