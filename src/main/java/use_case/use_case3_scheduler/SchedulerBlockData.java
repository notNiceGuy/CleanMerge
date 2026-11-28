// use_case/use_case3_scheduler/ScheduledBlockData.java
package use_case.use_case3_scheduler;

import java.time.LocalDateTime;

// Copy of one TimeBlock (Event or FocusBlock) for the presenter/view.
// Same idea as TaskData (UC1) and EventData (UC2): no entities leave the use case.
public class SchedulerBlockData {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final boolean fixed;     // true = imported Event, false = generated FocusBlock
    private final String taskId;     // null for Events

    public SchedulerBlockData(String id, String title, String description,
                              LocalDateTime start, LocalDateTime end,
                              boolean fixed, String taskId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.fixed = fixed;
        this.taskId = taskId;
    }

    // getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public boolean isFixed() { return fixed; }
    public String getTaskId() { return taskId; }
}