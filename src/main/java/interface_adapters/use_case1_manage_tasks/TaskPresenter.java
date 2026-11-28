package interface_adapters.use_case1_manage_tasks;

import interface_adapters.ViewModel;
import use_case.use_case1_manage_tasks.ManageTasksOutputBoundary;
import use_case.use_case1_manage_tasks.ManageTasksOutputData;
import use_case.use_case1_manage_tasks.TaskData;

import java.util.ArrayList;
import java.util.List;

public class TaskPresenter implements ManageTasksOutputBoundary {

    private final ViewModel<TaskListState> viewModel;

    public TaskPresenter(ViewModel<TaskListState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ManageTasksOutputData output) {
        List<TaskRow> rows = new ArrayList<>();
        for (TaskData t: output.getTasks()) {
            rows.add(new TaskRow(t.getId(), t.getTitle(), t.getDescription(), t.getEstimatedMinutes(),
                    formatMinutes(t.getEstimatedMinutes()), t.getCompleted(), formatStatus(t.getCompleted())));
        }
        String total = "Total: " + formatMinutes(output.getTotalMinutes());
        viewModel.setState(new TaskListState(rows, total, null));
    }

    // displays the old TaskListState but with an errorMessage added
    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setState(viewModel.getState().withError(errorMessage));
    }

    // turns an int value into a presentable Sting for screen
    static String formatMinutes(int minutes) {
        int hours = minutes / 60;
        int rest = minutes % 60;
        if (hours == 0) {
            return rest + " min";
        }
        if (rest == 0) {
            return hours + " h";
        }
        return hours + " h" + rest + " min";
    }

    // turns stored completed status into words for screen
    static String formatStatus(String completed) {
        if (completed.equals("YES")) {
            return "Done";
        }
        if (completed.equals("NO")) {
            return "Not done";
        }
        return "N/A";   // "NA"
    }
}
