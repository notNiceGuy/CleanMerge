package use_case.import_calendar;

public class ImportCalendarInputData {
    private final ImportSourceType sourceType;
    private final String sourceIdentifier;
    private final boolean replaceExisting;

    public ImportCalendarInputData(ImportSourceType sourceType, String sourceIdentifier, boolean replaceExisting) {
        this.sourceType = sourceType;
        this.sourceIdentifier = sourceIdentifier;
        this.replaceExisting = replaceExisting;
    }

    //getters
    public ImportSourceType getSourceType() {
        return sourceType;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public boolean isReplaceExisting() {
        return replaceExisting;
    }

}
