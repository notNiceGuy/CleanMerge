package use_case.import_calendar;

import java.util.List;

public class ImportCalendarOutputData {
    private final List<EventData> events;
    private final int importedCount;
    private final String statusMessage;

    public ImportCalendarOutputData(List<EventData> events, int importedCount, String statusMessage) {
        this.events = List.copyOf(events);
        this.importedCount = importedCount;
        this.statusMessage = statusMessage;
    }

    // getters
    public List<EventData> getEvents() {
        return events;
    }

    public int getImportedCount() {
        return importedCount;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}