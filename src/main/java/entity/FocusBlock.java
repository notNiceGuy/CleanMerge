package entity;

import java.time.LocalDateTime;

public class FocusBlock extends TimeBlock {

    private final String taskId; // which Task this session is for

    public FocusBlock(String id, String taskId, String title, String description, LocalDateTime start, LocalDateTime end) {
        super(id, title, description, start, end);
        if (taskId == null) {
            throw new IllegalArgumentException("A focus block must belong to a task");
        }
        this.taskId = taskId;
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    // Returns a copy of this block starting at the new Start, keeping everything else the same.
    // Method for Use Case 4, used when student drags a new block to a new time
    public FocusBlock movedTo(LocalDateTime newStart) {
        LocalDateTime newEnd = newStart.plusMinutes(getDurationMinutes());
        return new FocusBlock(getId(), taskId, getTitle(), getDescription(), newStart, newEnd);
    }

    // getters
    public String getTaskId() {
        return taskId;
    }
}

