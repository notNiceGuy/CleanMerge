package interface_adapters.use_case1_manage_tasks;

import java.util.List;

public class TaskListState {

    private final List<TaskRow> rows;
    private final String totalLabel;
    private final String error;

    public TaskListState(List<TaskRow> rows, String totalLabel, String error) {
        this.rows = List.copyOf(rows);
        this.totalLabel = totalLabel; // total time+
        this.error = error; // null if last action worked
    }

    // What screen shows before student has done anything
    public static TaskListState empty() {
        return new TaskListState(List.of(), "Total: 0 min", null);
    }

    // creates a new object with values same as old TaskListState but with an errorMessage
    public TaskListState withError(String message) {
        return new TaskListState(rows, totalLabel, message);
    }

    // getters
    public List<TaskRow> getRows() {
        return rows;
    }

    public String getTotalLabel() {
        return totalLabel;
    }

    // returns null if no error to show
    public String getError() {
        return error;
    }
}
