package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schedule {

    private final List<TimeBlock> timeBlocks;
    private final String id;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Schedule(String id, LocalDateTime start, LocalDateTime end){
        if (id == null) {
            throw new IllegalArgumentException("Block id is required");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end times are required");
        }
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        this.id = id;
        this.start = start;
        this.end = end;
        this.timeBlocks = new ArrayList<>();
    }
    public void addTimeBlock(TimeBlock timeBlock){
        // Precondition: The caller must add a timeBlock that does not come before this.start and this.end
        if (timeBlocks.isEmpty()){
            timeBlocks.add(timeBlock);
        }
        else if (checkValidAdd(timeBlock)){
            // edge case: the inserted timeBlock happens to be before every other timeBlock
            if (timeBlock.getEnd().isBefore(timeBlocks.get(0).getStart())){
                timeBlocks.add(0, timeBlock);
                return;
            }
            // handles the case where the inserted timeBlock is in between two other timeBlocks
            for (int i = 0; i < timeBlocks.size() - 1; i++){
                if (timeBlock.getStart().isAfter(timeBlocks.get(i).getEnd()) &&
                timeBlock.getEnd().isBefore(timeBlocks.get(i+1).getStart())){
                    timeBlocks.add(i+1, timeBlock);
                    return;
                }
            }
            // edge case: the inserted timeBlock happens to be after every other timeBlock
            if (timeBlock.getStart().isAfter(timeBlocks.get(timeBlocks.size() - 1).getStart())) {
                timeBlocks.add(timeBlocks.size() - 1, timeBlock);
                // return;
            }
        }
        else{
            throw new IllegalArgumentException("The argument has time conflict with other timeblocks within the schedule");
        }
    }
    private boolean checkValidAdd(TimeBlock timeBlock){
        for (TimeBlock currTimeBlock : timeBlocks){
            if (timeBlock.overlaps(currTimeBlock)){
                return false;
            }
        }
        return true;
    }

    // return whether successfully removed a TimeBlock based on the provided blockId
    public boolean removeTimeBlock(String blockId){
        for (TimeBlock currTimeBlock : timeBlocks){
            if (currTimeBlock.getId().equals(blockId)){
                timeBlocks.remove(currTimeBlock);
                return true;
            }
        }
        return false;
    }

    // Swaps an existing block for an updated one (e.g. FocusBlock.movedTo for use case 4).
// If the new block conflicts, the old one is put back and the exception is rethrown.
    public void replaceTimeBlock(String blockId, TimeBlock updated) {
        // Find the block with the matching id
        TimeBlock old = null;
        for (TimeBlock b : timeBlocks) {
            if (b.getId().equals(blockId)) {
                old = b;
                break;
            }
        }
        if (old == null) {
            throw new IllegalArgumentException("No block with id " + blockId);
        }

        // Take the old block out so it doesn't count as a conflict with the updated one
        timeBlocks.remove(old);
        try {
            addTimeBlock(updated);
        } catch (IllegalArgumentException e) {
            // The updated block didn't fit, so put the old one back and pass the error on
            addTimeBlock(old);
            throw e;
        }
    }

    // True if nothing in the schedule overlaps [from, to). Back-to-back blocks are allowed.
    public boolean isFree(LocalDateTime from, LocalDateTime to) {
        for (TimeBlock b : timeBlocks) {
            if (b.overlaps(from, to)) {
                return false;
            }
        }
        return true;
    }

    // getters
    public String getId() { return id; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }

    // Read-only view so callers can't skip the conflict check.
    public List<TimeBlock> getTimeBlocks() {
        return Collections.unmodifiableList(timeBlocks);
    }

}
