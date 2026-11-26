package use_case.import_calendar;

import entity.Event;
import use_case.EventDataAccessInterface;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ImportCalendarInteractor implements ImportCalendarInputBoundary {
    private final EventDataAccessInterface eventStorage;
    private final ImportCalendarOutputBoundary importCalendarPresenter;
    private final Map<ImportSourceType, CalendarSourceGateway> gateways = new EnumMap<>(ImportSourceType.class);

    public ImportCalendarInteractor(EventDataAccessInterface eventStorage,
                                    ImportCalendarOutputBoundary importCalendarPresenter,
                                    List<CalendarSourceGateway> calendarGateways) {
        this.eventStorage = eventStorage;
        this.importCalendarPresenter = importCalendarPresenter;
        if (calendarGateways != null) {
            for (CalendarSourceGateway gateway : calendarGateways) {
                if (gateway != null) {
                    gateways.put(gateway.getSourceType(), gateway);
                }
            }
        }

    }

    @Override
    public void importCalendar(ImportCalendarInputData input) {
        if (input == null || input.getSourceType() == null) {
            importCalendarPresenter.prepareFailView("Please select a calendar source.");
            return;
        }

        CalendarSourceGateway gateway = gateways.get(input.getSourceType());
        if (gateway == null) {
            importCalendarPresenter.prepareFailView(
                    "Unsupported calendar source: " + input.getSourceType());
            return;
        }
        List<Event> fetchedEvents;
        try {
            fetchedEvents = gateway.fetchEvents(input);
        } catch (CalendarImportException e) {
            importCalendarPresenter.prepareFailView(e.getMessage());
            return;
        }

        if (input.isReplaceExisting()) {
            eventStorage.deleteAllEvents();
        }
        for (Event event : fetchedEvents) {
            eventStorage.saveEvent(event);
        }

        presentEvents(fetchedEvents.size(), "Imported " + fetchedEvents.size() + " events.");
    }

    @Override
    public void clearCalendar() {
        boolean deleted = eventStorage.deleteAllEvents();
        if (!deleted) {
            importCalendarPresenter.prepareFailView("No events to clear, or failed to clear events.");
            return;
        }
        presentEvents(0, "Cleared all events.");
    }

    private void presentEvents(int importedCount, String statusMessage) {
        List<EventData> rows = new ArrayList<>();
        for (Event event : eventStorage.getAllEvents()) {
            rows.add(new EventData(event.getId(), event.getTitle(), event.getDescription(),
                    event.getStart(), event.getEnd()));
        }
        importCalendarPresenter.prepareSuccessView(new ImportCalendarOutputData(rows, importedCount, statusMessage));
    }
}
