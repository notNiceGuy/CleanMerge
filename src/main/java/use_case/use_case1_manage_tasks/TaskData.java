package use_case.use_case1_manage_tasks;

// copy of a Task object
// Used as a buffer for UC1 to Task objects, only used for presentation purposes in presenter and view
// Helps with loose coupling

public class TaskData {
    private final String id;              // kept so the view can send it back for edit/delete
    private final String title;
    private final int estimatedMinutes;
    private final String description;     // "" if none (Task never stores null)
    private final String completed;       // "YES" or "NO", always in capitals

    public TaskData(String id, String title, int estimatedMinutes, String description, String completed) {
        this.id = id;
        this.title = title;
        this.estimatedMinutes = estimatedMinutes;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
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

    public String getCompleted() {
        return completed;
    }
}
