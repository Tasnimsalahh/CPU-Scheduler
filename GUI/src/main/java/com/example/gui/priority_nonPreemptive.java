package com.example.gui;

import java.util.List;
public class priority_nonPreemptive extends Scheduler{
    public priority_nonPreemptive(List<Job> jobs) {
        super(jobs);
    }

    @Override
    protected Job startScheduler() {
        if(jobs.isEmpty()){return null;}
        for (Job j: jobs) {
            if (j.getStatus() == Job.RUNNING) j.setStatus(Job.WAITING);  // setting all non-terminated jobs to waiting
        }
        Job currentJob = checkForRunningJob();
        if (currentJob != null){                      // non-preemptive property
            currentJob.setStatus(Job.RUNNING);
            currentJob.setRemainingTime(currentJob.getRemainingTime()-1);
            if(currentJob.getRemainingTime() == 0) dequeue(currentJob);
            return currentJob;
        }
        else {
            Job highestPriority = nextJob();
            if (highestPriority == null) return null;
            highestPriority.setStartTime(getCurrentTime());
            highestPriority.setStatus(Job.RUNNING);
            highestPriority.setRemainingTime(highestPriority.getRemainingTime()-1);
            if (highestPriority.getRemainingTime()==0) dequeue(highestPriority);
            return highestPriority;
        }
    }

    private Job checkForRunningJob(){
        for (Job j: jobs) {
            if (j.getBurstTime() != j.getRemainingTime() && availToRun(j)){
                return j;
            }
        }
        return null;
    }
    private Job nextJob(){             // selects job with the highest priority

        Job highestPriorityJob = jobs.get(0);
        for (int i = 1; i < jobs.size(); i++) {
            Job currentJob = jobs.get(i);
            if (notAvailToRun(currentJob)) continue;
            if (currentJob.getPriorityLevel() < highestPriorityJob.getBurstTime() || notAvailToRun(highestPriorityJob)) {
                highestPriorityJob = currentJob;
            }
        }
        if (availToRun(highestPriorityJob)) return highestPriorityJob;
        else return null;
    }
}
