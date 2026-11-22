package interface_adapters.use_case1_manage_tasks;

// One row on the to-do list screen

public class TaskRow {

    private final String taskId; // not shown, it is sent back to controller for edit/delete task
    private final String title;
    private final String description;
    private final int estimatedMinutes;
    private final String durationLabel;
    private final String completed;
    private final String statusLabel;

    public TaskRow(String taskId, String title, String description, int estimatedMinutes,
                   String durationLabel, String completed, String statusLabel) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.estimatedMinutes = estimatedMinutes;
        this.durationLabel = durationLabel; // for View since it has to be a String
        this.completed = completed;
        this.statusLabel = statusLabel; // for View since it has to be a String
    }

    // getters
    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public String getDurationLabel() {
        return durationLabel;
    }

    public String getCompleted() {
        return completed;
    }

    public String getStatusLabel() {
        return statusLabel;
    }
}
