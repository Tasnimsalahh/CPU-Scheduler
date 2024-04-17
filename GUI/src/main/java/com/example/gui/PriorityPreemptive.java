package com.example.gui;

import java.util.List;

public class PriorityPreemptive extends Scheduler {

    public PriorityPreemptive(List<Job> jobs) {
        super(jobs);
    }

    @Override
    protected Job startScheduler() {
        if (jobs.isEmpty()) return null;

        // set all non-terminated jobs to waiting
        for (Job j: jobs) {
            if (j.getStatus() == Job.RUNNING) j.setStatus(Job.WAITING);
        }

        // Find next job
        Job highestPriorityJob = jobs.get(0);
        for (Job j: jobs) {
            if (notAvailToRun(j)) continue;
            if (j.getPriorityLevel() < highestPriorityJob.getPriorityLevel() || notAvailToRun(highestPriorityJob)) { // elimnate terminated jobs from the comparison
                highestPriorityJob = j;
                highestPriorityJob.setStatus(Job.RUNNING);
            }
        }
        // check if no jobs are ready
        if (notAvailToRun(highestPriorityJob)) return null;
        // set start time
        if (highestPriorityJob.getRemainingTime() == highestPriorityJob.getBurstTime())
            highestPriorityJob.setStartTime(getCurrentTime());

        // set remaining time
        highestPriorityJob.setRemainingTime(highestPriorityJob.getRemainingTime() - 1);
        if (highestPriorityJob.getRemainingTime() == 0) {
            dequeue(highestPriorityJob);
        }

        return highestPriorityJob;
    }
}
