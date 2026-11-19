package use_case.manage_tasks;

import entity.Task;
import use_case.TaskDataAccessInterface;

import java.util.UUID;

public class ManageTasksInteractor implements ManageTasksInputBoundary {
    private final TaskDataAccessInterface dataAccess;
    private final ManageTasksOutputBoundary presenter;

    public ManageTasksInteractor(TaskDataAccessInterface dataAccess, ManageTasksOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void addTask(ManageTasksInputData input) {
        String newId = UUID.randomUUID().toString();
        saveTask(newId, input);
    }

    @Override
    public void editTask(ManageTasksInputData input) {

    }

    @Override
    public void deleteTask(String taskId) {

    }

    private void saveTask(String id, ManageTasksInputData input) {
        Task task;
        try {
            task = new Task(id, input.getTitle(), input.getEstimatedMinutes(), input.getDescription(), input.getCompleted());
        } catch (IllegalArgumentException invalid) {
            presenter.prepareFailView(invalid.getMessage());
            return;
        }
        dataAccess.saveTask(task);
    }
}
