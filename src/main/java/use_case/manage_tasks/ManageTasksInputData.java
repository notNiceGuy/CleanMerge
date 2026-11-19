package use_case.manage_tasks;

import java.time.LocalDateTime;

public class ManageTasksInputData {

    private final String taskId;
    private final String title;
    private final int estimatedMinutes;
    private final String description;
    private final boolean completed;

    public ManageTasksInputData(String taskId, String title, int estimatedMinutes, String description) {
        this.taskId = taskId;
        this.title = title;
        this.estimatedMinutes = estimatedMinutes;
        this.description = description;
        this.completed = false;
    }

    // getters
    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompleted() {return completed; }
}
