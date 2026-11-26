package interface_adapters.import_calendar;

import java.util.List;

public class ImportCalendarState {
    private final List<EventRow> eventRows;
    private final String statusMessage;
    private final String errorMessage;

    public ImportCalendarState(List<EventRow> eventRows, String statusMessage, String errorMessage) {
        this.eventRows = List.copyOf(eventRows);
        this.statusMessage = statusMessage;
        this.errorMessage = errorMessage;
    }

    public static ImportCalendarState empty() {
        return new ImportCalendarState(List.of(), "No events imported yet.", null);
    }

    public ImportCalendarState withError(String message) {
        return new ImportCalendarState(eventRows, statusMessage, message);
    }

    public List<EventRow> getEventRows() {
        return eventRows;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
