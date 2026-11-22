package use_case.use_case1_manage_tasks;

import entity.Task;
import use_case.TaskDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManageTasksInteractor implements ManageTasksInputBoundary {
    private final static String TASK_NOT_FOUND = "That task no longer exists";

    private final ManageTasksOutputBoundary presenter;
    private final TaskDataAccessInterface taskStorage;

    public ManageTasksInteractor(ManageTasksOutputBoundary presenter, TaskDataAccessInterface taskStorage) {
        this.presenter = presenter;
        this.taskStorage = taskStorage;
    }

    // use case methods

    @Override
    public void addTask(ManageTasksInputData input) {
        String newId = UUID.randomUUID().toString(); // generate a random ID to store
        saveAndPresentTask(newId, input);
    }

    @Override
    public void editTask(ManageTasksInputData input) {
        String id = input.getTaskId();
        if (id == null) {
            presenter.prepareFailView(TASK_NOT_FOUND);
            return;
        }
        saveAndPresentTask(id, input);
    }

    @Override
    public void deleteTask(String taskId) {
        // deleteTask returns True if a task with taskId is deleted
        if (taskId == null || !taskStorage.deleteTask(taskId)) {
            presenter.prepareFailView(TASK_NOT_FOUND);
            return;
        }
        presentTaskList();
    }


    // helper functions

    public void saveAndPresentTask(String id, ManageTasksInputData input) {
        Task task;
        try {
            task = new Task(id, input.getTitle(), input.getEstimatedMinutes(), input.getDescription(), input.getCompleted());
        } catch (IllegalArgumentException invalid) {
            presenter.prepareFailView(invalid.getMessage());
            return;
        }
        taskStorage.saveTask(task);
        presentTaskList();
    }

    public void presentTaskList() {
        List<TaskData> rows = new ArrayList<>();
        int totalMinutes = 0;

        for (Task t: taskStorage.getAllTasks()) {
            rows.add(new TaskData(t.getId(), t.getTitle(), t.getEstimatedMinutes(), t.getDescription(), t.getCompleted()));
            totalMinutes += t.getEstimatedMinutes();
        }

        presenter.prepareSuccessView(new ManageTasksOutputData(rows, totalMinutes));
    }
}
