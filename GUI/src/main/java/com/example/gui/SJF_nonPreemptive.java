package com.example.gui;

import java.util.List;
public class SJF_nonPreemptive extends Scheduler{

    public SJF_nonPreemptive(List<Job> jobs){
        super(jobs);
    }

    protected Job startScheduler(){
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
            Job shortestjob = nextJob();
            if (shortestjob == null) return null;
            shortestjob.setStartTime(getCurrentTime());
            shortestjob.setStatus(Job.RUNNING);
            shortestjob.setRemainingTime(shortestjob.getRemainingTime()-1);
            if (shortestjob.getRemainingTime() == 0) dequeue(shortestjob);

            return shortestjob;
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
    private Job nextJob(){

        Job shortestJob = jobs.get(0);
        for (int i = 1; i < jobs.size(); i++) {
            Job currentJob = jobs.get(i);
            if (notAvailToRun(currentJob)) continue;
            if (currentJob.getRemainingTime() < shortestJob.getRemainingTime() || notAvailToRun(shortestJob)) {
                shortestJob = currentJob;
            }
        }
        if (availToRun(shortestJob)) return shortestJob;
        else return null;
    }

}
