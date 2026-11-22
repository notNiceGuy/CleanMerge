package use_case.use_case1_manage_tasks;

public interface ManageTasksOutputBoundary {
    void prepareSuccessView(ManageTasksOutputData output);
    void prepareFailView(String errorMessage);
}
