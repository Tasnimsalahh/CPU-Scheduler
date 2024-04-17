package com.example.gui;

import java.util.List;

public class SJF_Preemptive extends Scheduler {

    public SJF_Preemptive(List<Job> jobs) {
        super(jobs);
    }

    @Override
    protected Job startScheduler() {
        if (jobs.isEmpty()) return null;

        // set all non-terminated jobs to waiting
        for (Job j: jobs) {
            if (j.getStatus() == Job.RUNNING) j.setStatus(Job.WAITING);
        }

        // Find the shortest job
        Job shortestJob = jobs.get(0);
        for (Job j: jobs) {
            if (notAvailToRun(j)) continue;
            if (j.getRemainingTime() < shortestJob.getRemainingTime() || notAvailToRun(shortestJob)) { // eliminate terminated jobs from the comparison
                shortestJob = j;
                shortestJob.setStatus(Job.RUNNING);
            }
        }
        // check if no jobs are ready
        if (notAvailToRun(shortestJob)) return null;
        // set start time
        if (shortestJob.getRemainingTime() == shortestJob.getBurstTime())
            shortestJob.setStartTime(getCurrentTime());
        // set remaining time
        shortestJob.setRemainingTime(shortestJob.getRemainingTime() - 1);
        if (shortestJob.getRemainingTime() == 0) {
            dequeue(shortestJob);
        }

        return shortestJob;
    }
}
