package use_case.import_calendar;

/**
 * Enum representing the types of calendar sources that can be imported.
 */
public enum ImportSourceType {
    ICS_FILE(".ics file"),
    GOOGLE_CALENDAR("Google Calendar");

    private final String displayName;

    ImportSourceType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
