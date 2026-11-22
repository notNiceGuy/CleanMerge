package use_case.use_case1_manage_tasks;

public interface ManageTasksInputBoundary {
    void addTask(ManageTasksInputData input);
    void editTask(ManageTasksInputData input);
    void deleteTask(String id);
}
