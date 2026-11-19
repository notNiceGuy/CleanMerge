package entity;

// One item on student's to-do list
// Immutable: editing a task means saving a new Task with the same id

public class Task {
    private final String id;
    private final String title;
    private final int estimatedMinutes;
    private final String description; // optional, "" if user didn't input any
    private final String completed; // has to be "NA", "YES" or "NO"

    public Task(String id, String title, int estimatedMinutes, String description, String completed) {
        if (id == null) {
            throw new IllegalArgumentException("Task id is required");
        }
        if (title == null) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        if (estimatedMinutes <= 0) {
            throw new IllegalArgumentException("Estimated time must be greater than 0 minutes");
        }
        if (!(completed.equals("na") || completed.equals("Yes") || completed.equals("NO"))) {
            throw new IllegalArgumentException("Completed must be \"NA\", \"YES\" or \"NO\"");
        }
        this.id = id;
        this.title = title.strip();
        this.estimatedMinutes = estimatedMinutes;
        if (description == null) {
            this.description = "";
        } else {
            this.description = description.strip();
        }
        this.completed = completed;
    }

    // getters
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
