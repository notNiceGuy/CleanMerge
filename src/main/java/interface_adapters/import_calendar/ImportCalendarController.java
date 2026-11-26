package interface_adapters.import_calendar;

import use_case.import_calendar.ImportCalendarInputBoundary;
import use_case.import_calendar.ImportCalendarInputData;
import use_case.import_calendar.ImportSourceType;

public class ImportCalendarController {
    private static final String DEFAULT_CALENDAR_ID = "primary";
    private final ImportCalendarInputBoundary interactor;

    public ImportCalendarController(ImportCalendarInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void importFromIcsFile(String filePath, boolean replaceExisting) {
        interactor.importCalendar(new ImportCalendarInputData(ImportSourceType.ICS_FILE, filePath, replaceExisting));
    }

    public void importFromGoogleCalendar(boolean replaceExisting) {
        interactor.importCalendar(new ImportCalendarInputData(ImportSourceType.GOOGLE_CALENDAR, DEFAULT_CALENDAR_ID, replaceExisting));
    }

    public void clearCalendar() {
        interactor.clearCalendar();
    }
}
