package use_case.use_case3_scheduler;

import entity.Schedule;

public class SchedulerInteractor {

    private int focusLength;
    private Schedule schedule;


    public void ScheduleInteractor(int focusLength, Schedule schedule) {

        this.focusLength = focusLength;
        this.schedule = schedule;

    }

}
