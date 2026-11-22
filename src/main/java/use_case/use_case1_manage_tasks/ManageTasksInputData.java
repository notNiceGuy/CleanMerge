package use_case.use_case1_manage_tasks;

public class ManageTasksInputData {
    private final String taskId;
    private final String title;
    private final String description;
    private final int estimatedMinutes;
    private final String completed;

    public ManageTasksInputData(String taskId, String title, String description, int estimatedMinutes, String completed) {
        this.taskId = taskId; // null when adding a new Task
        this.title = title;
        this.description = description; // can be "" if user didn't input any description
        this.estimatedMinutes = estimatedMinutes;
        this.completed = completed;
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

    public String getCompleted() {
        return completed;
    }
}
