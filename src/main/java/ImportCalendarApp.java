// Temporary class for Import Calendar testing purposes

import data_access.IcsCalendarGateway;
import data_access.InMemoryDataAccessObject;
import interface_adapters.ViewModel;
import interface_adapters.import_calendar.ImportCalendarController;
import interface_adapters.import_calendar.ImportCalendarPresenter;
import interface_adapters.import_calendar.ImportCalendarState;
import use_case.EventDataAccessInterface;
import use_case.import_calendar.ImportCalendarInteractor;
import view.ImportCalendarView;

import javax.swing.*;
import java.util.List;
import java.util.Locale;

public class ImportCalendarApp {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SwingUtilities.invokeLater(() -> {

            EventDataAccessInterface storage = new InMemoryDataAccessObject();

            ViewModel<ImportCalendarState> viewModel = new ViewModel<>(ImportCalendarState.empty());

            ImportCalendarPresenter presenter = new ImportCalendarPresenter(viewModel);
            ImportCalendarInteractor interactor =
                    new ImportCalendarInteractor(storage, presenter, List.of(new IcsCalendarGateway()));
            ImportCalendarController controller = new ImportCalendarController(interactor);

            ImportCalendarView view = new ImportCalendarView(viewModel, controller);

            JFrame frame = new JFrame("Import Calendar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // closing the window ends the program
            frame.add(view);
            frame.setSize(900, 450);
            frame.setLocationRelativeTo(null);   // centre on the screen
            frame.setVisible(true);

        });
    }
}
