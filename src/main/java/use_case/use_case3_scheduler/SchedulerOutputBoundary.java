// use_case/use_case3_scheduler/SchedulerOutputBoundary.java
package use_case.use_case3_scheduler;

public interface SchedulerOutputBoundary {
    void prepareSuccessView(SchedulerOutputData output);
    void prepareFailView(String errorMessage);
}