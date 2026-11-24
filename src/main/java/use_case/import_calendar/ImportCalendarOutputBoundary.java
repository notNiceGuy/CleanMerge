package use_case.import_calendar;

public interface ImportCalendarOutputBoundary {
    void prepareSuccessView(ImportCalendarOutputData outputData);
    void prepareFailView(String errorMessage);
}
