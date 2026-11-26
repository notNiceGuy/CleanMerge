package use_case.import_calendar;

import java.time.LocalDateTime;

public class EventData {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public EventData(String id, String title, String description, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
