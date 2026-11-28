package use_case.use_case3_scheduler;

import entity.Schedule;
import use_case.EventDataAccessInterface;
import use_case.ScheduleDataAccessInterface;
import use_case.TaskDataAccessInterface;

public class SchedulerInteractor implements SchedulerInputBoundary {

    private final TaskDataAccessInterface taskDataAccess;
    private final EventDataAccessInterface eventDataAccess;
    private final ScheduleDataAccessInterface scheduleDataAccess;
    private final SchedulerOutputBoundary presenter;

    public SchedulerInteractor(TaskDataAccessInterface taskDataAccess,
                               EventDataAccessInterface eventDataAccess,
                               ScheduleDataAccessInterface scheduleDataAccess,
                               SchedulerOutputBoundary presenter) {

        this.taskDataAccess = taskDataAccess;
        this.eventDataAccess = eventDataAccess;
        this.scheduleDataAccess = scheduleDataAccess;
        this.presenter = presenter;

    }

        @Override
        public void generateSchedule(SchedulerInputData input) {
            // ...
        }
    }

}
