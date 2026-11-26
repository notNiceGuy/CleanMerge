package view;

import interface_adapters.ViewModel;
import interface_adapters.import_calendar.EventRow;
import interface_adapters.import_calendar.ImportCalendarController;
import interface_adapters.import_calendar.ImportCalendarState;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ImportCalendarView extends JPanel implements PropertyChangeListener {
    private static final String[] COLUMNS = {"Title", "Start", "End", "Duration", "Description"};

    private final ViewModel<ImportCalendarState> viewModel;
    private final ImportCalendarController controller;

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(tableModel);
    private final JButton importButton = new JButton("Import Calendar");
    private final JPopupMenu importMenu = new JPopupMenu();
    private final JMenuItem icsMenuItem = new JMenuItem("Import .ics File");
    private final JMenuItem googleMenuItem = new JMenuItem("Import Google Calendar");
    private final JButton clearButton = new JButton("Clear Calendar");
    private final JLabel statusLabel = new JLabel();
    private final JLabel errorLabel = new JLabel(" ");

    public ImportCalendarView(ViewModel<ImportCalendarState> viewModel, ImportCalendarController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        viewModel.addPropertyChangeListener(this);

        buildLayout();
        connectActions();
        showState(viewModel.getState());
    }

    private void buildLayout() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        importMenu.add(icsMenuItem);
        importMenu.add(googleMenuItem);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonRow.add(importButton);
        buttonRow.add(clearButton);

        JPanel infoRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorLabel.setForeground(new Color(180, 0, 0));
        infoRow.add(statusLabel);
        infoRow.add(Box.createHorizontalStrut(20));
        infoRow.add(errorLabel);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(buttonRow);
        bottom.add(infoRow);
        add(bottom, BorderLayout.SOUTH);

    }

    private void connectActions() {
        importButton.addActionListener(e -> importMenu.show(importButton, 0, importButton.getHeight()));
        icsMenuItem.addActionListener(e -> onImportIcs());
        googleMenuItem.addActionListener(e -> onImportGoogle());
        clearButton.addActionListener(e -> controller.clearCalendar());
    }

    private Boolean askReplaceExisting() {
        if (tableModel.getRowCount() == 0) {
            return false;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Replace existing events?\nYes: Replace existing events\nNo: Keep existing events and add new ones",
                "Import Options",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            return true;
        } else if (choice == JOptionPane.NO_OPTION) {
            return false;
        } else {
            return null;
        }
    }

    private void onImportIcs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("iCalendar files (*.ics)", "ics"));
        int result = chooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Boolean replaceExisting = askReplaceExisting();
        if (replaceExisting == null) {
            return;
        }

        controller.importFromIcsFile(chooser.getSelectedFile().getAbsolutePath(), replaceExisting);
    }

    private void onImportGoogle() {
        Boolean replaceExisting = askReplaceExisting();
        if (replaceExisting == null) {
            return;
        }

        controller.importFromGoogleCalendar(replaceExisting);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        showState(viewModel.getState());
    }

    private void showState(ImportCalendarState state) {
        if (state.getErrorMessage() != null) {
            errorLabel.setText(state.getErrorMessage());
            return;
        }

        tableModel.setRowCount(0);
        for (EventRow row : state.getEventRows()) {
            tableModel.addRow(new Object[]{
                    row.getTitle(),
                    row.getStartTime(),
                    row.getEndTime(),
                    row.getDuration(),
                    row.getDescription()
            });
        }

        statusLabel.setText(state.getStatusMessage());
        errorLabel.setText(" ");
    }
}
