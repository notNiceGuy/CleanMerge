// use_case/use_case3_scheduler/SchedulerOutputData.java
package use_case.use_case3_scheduler;

import java.util.List;

public class SchedulerOutputData {
    private final List<SchedulerBlockData> blocks;       // sorted by start time; breaks are the gaps
    private final List<String> unscheduledTaskTitles;    // tasks (or leftover parts) that didn't fit
    private final String statusMessage;                  // e.g. "Scheduled 7 focus blocks over 3 days"

    public SchedulerOutputData(List<SchedulerBlockData> blocks,
                               List<String> unscheduledTaskTitles,
                               String statusMessage) {
        this.blocks = List.copyOf(blocks);
        this.unscheduledTaskTitles = List.copyOf(unscheduledTaskTitles);
        this.statusMessage = statusMessage;
    }

    // getters
    public List<SchedulerBlockData> getBlocks() { return blocks; }
    public List<String> getUnscheduledTaskTitles() { return unscheduledTaskTitles; }
    public String getStatusMessage() { return statusMessage; }
}