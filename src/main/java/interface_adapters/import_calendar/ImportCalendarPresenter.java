package interface_adapters.import_calendar;

import interface_adapters.ViewModel;
import use_case.import_calendar.EventData;
import use_case.import_calendar.ImportCalendarOutputBoundary;
import use_case.import_calendar.ImportCalendarOutputData;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportCalendarPresenter implements ImportCalendarOutputBoundary {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ViewModel<ImportCalendarState> viewModel;

    public ImportCalendarPresenter(ViewModel<ImportCalendarState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ImportCalendarOutputData outputData) {
        List<EventRow> eventRows = new ArrayList<>();
        for (EventData event : outputData.getEvents()) {
            String formattedStartTime = event.getStart().format(DATE_TIME_FORMATTER);
            String formattedEndTime = event.getEnd().format(DATE_TIME_FORMATTER);
            long duration = Duration.between(event.getStart(), event.getEnd()).toMinutes();
            String formattedDuration = formatDuration(duration);
            eventRows.add(new EventRow(event.getId(), event.getTitle(), event.getDescription(),
                    formattedStartTime, formattedEndTime, formattedDuration));
        }
        viewModel.setState(new ImportCalendarState(eventRows, outputData.getStatusMessage(), null));
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setState(viewModel.getState().withError(errorMessage));
    }

    static String formatDuration(long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        if (hours == 0) {
            return remainingMinutes + " min";
        }
        if (remainingMinutes == 0) {
            return hours + " h";
        }
        return hours + " h " + remainingMinutes + " min";
    }
}