package use_case.use_case1_manage_tasks;

import java.util.List;

public class ManageTasksOutputData {

    private final List<TaskData> tasks;
    private final int totalMinutes; // sum of every Task's estimate

    public ManageTasksOutputData(List<TaskData> tasks, int totalMinutes) {
        this.tasks = List.copyOf(tasks);
        this.totalMinutes = totalMinutes;
    }

    // getters
    public List<TaskData> getTasks() {
        return tasks;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }
}
