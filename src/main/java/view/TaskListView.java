package view;

import interface_adapters.ViewModel;
import interface_adapters.use_case1_manage_tasks.TaskController;
import interface_adapters.use_case1_manage_tasks.TaskListState;
import interface_adapters.use_case1_manage_tasks.TaskRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class TaskListView extends JPanel implements PropertyChangeListener {

    private static final String[] COLUMNS = {"Task", "Time", "Status", "Description"}; // column headings shown on top of table
    private static final String[] STATUSES = {"NA", "NO", "YES"};

    private final ViewModel<TaskListState> viewModel;
    private final TaskController controller;

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) { // creates a table with four columns, zero rows
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // make cells not directly editable on the screen, has to go through form to edit or add or delete a task
        }
    };

    private final JTable table = new JTable(tableModel); // creates the visible table

    // the form - input boxes that allow users to input tasks that are shown within the form
    private final JTextField titleField = new JTextField(18);
    private final JTextField minutesField = new JTextField(5);
    private final JTextField descriptionField = new JTextField(18);
    private final JComboBox<String> statusBox = new JComboBox<>(STATUSES); // drop-down selection box

    // buttons and labels
    private final JButton addButton = new JButton("Add");
    private final JButton saveButton = new JButton("Save changes");
    private final JButton deleteButton = new JButton("Delete");
    private final JLabel totalLabel = new JLabel();
    private final JLabel errorLabel = new JLabel(" "); // " " keeps its height when its empty

    // variable to store all the current rows displayed on the screen
    private List<TaskRow> currentRows = new ArrayList<>();

    public TaskListView(ViewModel<TaskListState> viewModel, TaskController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        viewModel.addPropertyChangeListener(this); // receives signal from ViewModel via firePropertyChange during setState function

        buildLayout();
        connectActions();
        showState(viewModel.getState());
    }

    // Layout
    private void buildLayout() {
        // overall frame of the layout
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // table at the top, grows with the window
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // user can only select one row at a time
        add(new JScrollPane(table), BorderLayout.CENTER); // adds table inside a scroll pane, centered

        // form row
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT)); // places components from left to right in the order they're added
        form.add(new JLabel("Title"));
        form.add(titleField);
        form.add(new JLabel("Minutes"));
        form.add(minutesField);
        form.add(new JLabel("Description"));
        form.add(descriptionField);
        form.add(new JLabel("Status"));
        form.add(statusBox);

        // button row
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(addButton);
        buttons.add(saveButton);
        buttons.add(deleteButton);

        // info row
        errorLabel.setForeground(new Color(180, 0, 0));   // color red
        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT));
        info.add(totalLabel);
        info.add(Box.createHorizontalStrut(20)); // invisible spacer so error isn't squished against the total
        info.add(errorLabel);

        // stacking the three rows at the bottom
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS)); // stack components from top to bottom in order of addition
        bottom.add(form);
        bottom.add(buttons);
        bottom.add(info);
        add(bottom, BorderLayout.SOUTH); // whole panel goes under the table
    }

    // User action -> Controller
    // Connecting buttons to methods
    public void connectActions() {
        addButton.addActionListener(e -> onAdd()); // addActionListener runs every time the button is clicked
        saveButton.addActionListener(e -> onSave());
        deleteButton.addActionListener(e -> onDelete());

        // Clicking a row copies that task into the form at the bottom
        // getSelectionModel keeps track of which rows is being selected
        table.getSelectionModel().addListSelectionListener(e-> { // addListSelectionListener runs every time the selection is changed (user clicked on a different row)
            if (!e.getValueIsAdjusting()) { // e.getValueIsAdjusting returns true if mouse is dragged across multiple rows
                fillFormFromSelectedRow();
            }
        });
    }


    // User click button action
    private void onAdd() {
        Integer minutes = readMinutes();
        if (minutes == null) {
            return;   // readMinutes already showed the error
        }
        controller.addTask(titleField.getText(), minutes, descriptionField.getText());
    }

    private void onSave() {
        TaskRow row = selectedRow();
        if (row == null) {
            return;
        }
        Integer minutes = readMinutes();
        if (minutes == null) {
            return;
        }
        controller.editTask(row.getTaskId(), titleField.getText(), descriptionField.getText(), minutes,
                (String) statusBox.getSelectedItem());
    }

    private void onDelete() {
        TaskRow row = selectedRow();
        if (row == null) {
            return;
        }
        controller.deleteTask(row.getTaskId());
    }

    // ViewModel -> screen
    // Called everytime presenter calls viewModel.setState()
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        showState(viewModel.getState());
    }

    public void showState(TaskListState state) {
        if (state.getError() != null) { // ran if presenter sends an error (prepareFailview) with the message
            errorLabel.setText(state.getError());
            return;
        }
        // Successful action or starting table: rebuild the table
        currentRows = state.getRows();
        tableModel.setRowCount(0); // remove the old rows, table header is not a row
        for (TaskRow row: currentRows) {
            tableModel.addRow(new Object[] {row.getTitle(), row.getDurationLabel(), row.getStatusLabel(), row.getDescription()}); // methods from TaskRow class, matches the four header columns
        }
        totalLabel.setText(state.getTotalLabel());
        errorLabel.setText(" ");
        clearForm(); // empties the bottom of table until next action
    }



    // Helpers
    private Integer readMinutes() {
        String text = minutesField.getText().strip();
        if (text.isEmpty()) {
            errorLabel.setText("Enter the estimated minutes");
            return null;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) { // non-whole number error
            errorLabel.setText("Minutes must be a whole number");
            return null;
        }
    }

    private TaskRow selectedRow() {
        int index = table.getSelectedRow(); // gets the selected row index
        if (index < 0) {
            errorLabel.setText("Select a task in the list first");
            return null;
        }
        return currentRows.get(index);
    }

    private void fillFormFromSelectedRow() {
        int index = table.getSelectedRow();
        if (index < 0) { // user selected a row that doesn't contain a task, doesn't cause an error, just return nothing to display
            return;
        }
        TaskRow row = currentRows.get(index); // get the TaskRow from the list currentRows
        titleField.setText(row.getTitle());
        minutesField.setText(String.valueOf(row.getEstimatedMinutes()));
        descriptionField.setText(row.getDescription());
        statusBox.setSelectedItem(row.getCompleted());
        errorLabel.setText(" "); // clears any old error if a new row is selected
    }

    private void clearForm() {
        table.clearSelection();
        titleField.setText("");
        minutesField.setText("");
        descriptionField.setText("");
        statusBox.setSelectedItem("NO");
    }
}
