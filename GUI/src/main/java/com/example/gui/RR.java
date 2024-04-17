package com.example.gui;

import java.util.ArrayList;
import java.util.List;


public class RR extends Scheduler {
	private int quantumCount;
	private static int entryCount=0;
	private static List<Job> availableJobs = new ArrayList<Job>();


	public RR(List<Job> jobs , int timeQuantum) {
		super(jobs);
		this.quantumCount = timeQuantum;
	}

	@Override
	protected Job startScheduler() {
		if(jobs.isEmpty()) return null;
		
		// check if a new job arrived and add it to the existing available jobs
		for(Job j: jobs) {
			if (j.getStatus() == Job.TERMINATED) continue;
			if(j.getArrivalTime() == getCurrentTime()) {
				RR.availableJobs.add(j);
			}
		}
		
		// if no job arrived yet return nothing
		if(RR.availableJobs.isEmpty()) return null;
		
		//select the next job
		Job selectedJob = RR.availableJobs.get(0) ;
		if(entryCount==0) {
			if(selectedJob.getStatus()== Job.TERMINATED) {
				RR.availableJobs.remove(0);
				if(RR.availableJobs.isEmpty()) return null;
			}
			
			/* i'm reassigning selectedJob to index 0 again, because in case we removed index 0 in the previous
			   'if condition' , we need the new value that got shifted to index 0 */ 
			selectedJob = RR.availableJobs.get(0);
			selectedJob.setStatus(Job.RUNNING);
			entryCount++;
			
			// set start time
	        if (selectedJob.getRemainingTime() == selectedJob.getBurstTime())
	            selectedJob.setStartTime(getCurrentTime());
	        
			// set remaining time 
			selectedJob.setRemainingTime(selectedJob.getRemainingTime() - 1);
			if (selectedJob.getRemainingTime() == 0) {
				dequeue(selectedJob);
				entryCount=0;
			}
			
		}
		else if(entryCount < quantumCount) {
			
			selectedJob.setStatus(Job.RUNNING); //redundant 
			entryCount++;
			selectedJob.setRemainingTime(selectedJob.getRemainingTime() - 1);
			if (selectedJob.getRemainingTime() == 0) {
				dequeue(selectedJob);
				entryCount=0;
			}
		}
		else if(entryCount == quantumCount) {
			
			entryCount=0;
			if(selectedJob.getStatus()== Job.TERMINATED) {
				RR.availableJobs.remove(0);
				if(RR.availableJobs.isEmpty()) return null;
			}
			else {
				selectedJob.setStatus(Job.WAITING);
				RR.availableJobs.add(selectedJob);
				RR.availableJobs.remove(0);
			}
			
			selectedJob = RR.availableJobs.get(0);
			selectedJob.setStatus(Job.RUNNING); //redundant 
			entryCount++;
			
			// set start time in case the job enters for the first time 
	        if (selectedJob.getRemainingTime() == selectedJob.getBurstTime())
	            selectedJob.setStartTime(getCurrentTime());
	        
			// set remaining time 
			selectedJob.setRemainingTime(selectedJob.getRemainingTime() - 1);
			if (selectedJob.getRemainingTime() == 0) {
				dequeue(selectedJob);
				entryCount=0;
			}
		}
		
		return selectedJob;	
	}
}
