package entity;

import java.time.LocalDateTime;

public class Event extends TimeBlock {
    public Event(String id, String title, String description, LocalDateTime start, LocalDateTime end) {
        super(id, title, description, start, end);
    }

    @Override
    public boolean isFixed() {
        return true;
    }
}
