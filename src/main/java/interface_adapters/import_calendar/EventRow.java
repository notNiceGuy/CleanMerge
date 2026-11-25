package interface_adapters.import_calendar;

public class EventRow {
    private final String eventId;
    private final String title;
    private final String description;
    private final String startTime;
    private final String endTime;
    private final String durationTime;

    public EventRow(String eventId, String title, String description, String startTime, String endTime, String durationTime) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationTime = durationTime;
    }

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDurationTime() {
        return durationTime;
    }
}
