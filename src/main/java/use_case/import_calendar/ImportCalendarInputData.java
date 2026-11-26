package use_case.import_calendar;

public class ImportCalendarInputData {
    private final ImportSourceType sourceType;
    private final String sourceIdentifier;
    private final boolean replaceExisting;

    /**
     * Creates a new ImportCalendarInputData instance.
     *
     * @param sourceType       the type of calendar source to import from
     * @param sourceIdentifier the identifier for the source (e.g. file path for .ics)
     * @param replaceExisting  true to clear previously stored events before importing;
     *                         false to keep existing events and add the newly imported ones
     */
    public ImportCalendarInputData(ImportSourceType sourceType, String sourceIdentifier, boolean replaceExisting) {
        this.sourceType = sourceType;
        this.sourceIdentifier = sourceIdentifier;
        this.replaceExisting = replaceExisting;
    }

    // getters

    /**
     * Returns the type of calendar source selected for this import.
     *
     * @return the calendar source type
     */
    public ImportSourceType getSourceType() {
        return sourceType;
    }

    /**
     * Returns the identifier locating the calendar source
     *
     * @return the source identifier string
     */
    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    /**
     * Returns whether existing events in shared storage should be cleared before saving the newly imported events.
     *
     * @return true if existing events should be replaced, false if new events should be added alongside them
     */
    public boolean isReplaceExisting() {
        return replaceExisting;
    }

}
