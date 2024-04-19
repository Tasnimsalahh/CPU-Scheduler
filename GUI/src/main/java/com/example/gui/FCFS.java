package com.example.gui;

import java.util.List;
import java.util.Collections;

public class FCFS extends Scheduler{
    public FCFS(List<Job> jobs){
        super(jobs);
    }
    @Override
    protected Job startScheduler(){
        // if no job arrived yet return nothing
        if(jobs.isEmpty())
            return null;
        
        // for the jobs that have arrived rearrange them according to their arrival time
        arrangeJobs();
        
        for(Job j: jobs){
            if(availToRun(j)){
            j.setStatus(Job.RUNNING);
            j.setRemainingTime(j.getRemainingTime() - 1);
            if(j.getRemainingTime() == 0){
                dequeue(j);
            }
            return j;
            }
        }
        return null;
    }
    
    private void arrangeJobs(){
        Collections.sort(jobs, new ArrangeJobs());
    }

}

class ArrangeJobs implements java.util.Comparator<Job>{
    @Override
    public int compare(Job job1, Job job2){
        return job1.getArrivalTime() - job2.getArrivalTime();
    }
}

