package use_case.import_calendar;

/**
 * Exception thrown when reading or parsing events from an external calendar source fails.
 */
public class CalendarImportException extends Exception {

    /**
     * Creates a new CalendarImportException with a descriptive error message.
     *
     * @param message explanation of why the calendar import failed
     */
    public CalendarImportException(String message) {
        super(message);
    }

    /**
     * Creates a new CalendarImportException with an error message and the underlying cause.
     *
     * @param message explanation of why the calendar import failed
     * @param cause   the underlying exception that triggered this failure
     */
    public CalendarImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
