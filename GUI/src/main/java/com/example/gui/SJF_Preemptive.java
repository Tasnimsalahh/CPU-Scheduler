package com.example.gui;

import java.util.List;

public class SJF_Preemptive extends Scheduler {

    public SJF_Preemptive(List<Job> jobs) {
        super(jobs);
    }

    @Override
    public Job startScheduler() {
        if (jobs.isEmpty()) return null;

        // set all non-terminated jobs to waiting
        for (Job j: jobs) {
            if (j.getStatus() == Job.RUNNING) j.setStatus(Job.WAITING);
        }

        // Find shortest job
        Job shortestJob = jobs.get(0);
        for (Job j: jobs) {
            if (j.getStatus() == Job.TERMINATED) continue;
            if (j.getRemainingTime() < shortestJob.getRemainingTime()) {
                shortestJob = j;
                shortestJob.setStatus(Job.RUNNING);
            }
        }
        // set start time
        if (shortestJob.getRemainingTime() == shortestJob.getBurstTime())
            shortestJob.setStartTime(getSchedulerStepCounter());

        shortestJob.setRemainingTime(shortestJob.getRemainingTime() - 1);
        if (shortestJob.getRemainingTime() == 0) {
            dequeue(shortestJob);
        }

        return shortestJob;
    }

    @Override
    public void enqueue(Job job) {
        jobs.add(job);
    }

    @Override
    public void dequeue(Job job) {
        job.setFinishTime(getSchedulerStepCounter());
        job.setStatus(Job.TERMINATED);
    }
}
