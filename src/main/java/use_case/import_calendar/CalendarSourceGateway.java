package use_case.import_calendar;

import entity.Event;

import java.util.List;

/**
 * Interface for fetching events from different external calendar sources.
 */
public interface CalendarSourceGateway {

    /**
     * Returns the type of the calendar source.
     *
     * @return the type of the calendar source
     */
    ImportSourceType getSourceType();

    /**
     * Fetches events from the calendar source.
     *
     * @param input the input data for the import
     * @return the list of events
     * @throws CalendarImportException if the source cannot be read or parsed
     */
    List<Event> fetchEvents(ImportCalendarInputData input) throws CalendarImportException;
}
