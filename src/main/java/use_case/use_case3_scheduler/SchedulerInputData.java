// use_case/use_case3_scheduler/SchedulerInputData.java
package use_case.use_case3_scheduler;

import java.time.LocalDate;
import java.time.LocalTime;

public class SchedulerInputData {
    private final LocalDate startDate;   // first day to plan
    private final int numberOfDays;      // how many days to plan, starting at startDate
    private final LocalTime dayStart;    // earliest a focus block may start each day
    private final LocalTime dayEnd;      // latest a focus block may end each day
    private final int focusLength;       // max minutes per focus block
    private final int breakLength;       // minimum minutes between any two blocks (must be > 0)

    public SchedulerInputData(LocalDate startDate, int numberOfDays,
                              LocalTime dayStart, LocalTime dayEnd,
                              int focusLength, int breakLength) {
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.focusLength = focusLength;
        this.breakLength = breakLength;
    }

    // getters
    public LocalDate getStartDate() { return startDate; }
    public int getNumberOfDays() { return numberOfDays; }
    public LocalTime getDayStart() { return dayStart; }
    public LocalTime getDayEnd() { return dayEnd; }
    public int getFocusLength() { return focusLength; }
    public int getBreakLength() { return breakLength; }
}