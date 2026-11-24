package use_case.import_calendar;

public interface ImportCalendarInputBoundary {
    void importCalendar(ImportCalendarInputData input);
    void clearCalendar();
}
