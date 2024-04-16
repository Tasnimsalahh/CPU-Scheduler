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
            highestPriority.setStartTime(getCurrentTime());
            highestPriority.setStatus(Job.RUNNING);
            highestPriority.setRemainingTime(highestPriority.getRemainingTime()-1);
            if (highestPriority.getRemainingTime()==0) dequeue(highestPriority);
            return highestPriority;
        }
    }

    public void enqueue(Job job){
        jobs.add(job);
    }
    public void dequeue(Job job){
        job.setFinishTime(getCurrentTime());
        job.setStatus(Job.TERMINATED);
    }
    private Job checkForRunningJob(){
        for (Job j: jobs) {
            if (j.getBurstTime() != j.getRemainingTime()){
                return j;
            }
        }
        return null;
    }
    private Job nextJob(){

        Job highestPriority = jobs.get(0);
        for (int i = 1; i < jobs.size(); i++) {
            Job currentJob = jobs.get(i);
            if (currentJob.getStatus() == Job.TERMINATED) continue;
            else if (currentJob.getPriorityLevel() < highestPriority.getBurstTime()) {
                highestPriority = currentJob;
            }
        }
        return highestPriority;
    }
}
