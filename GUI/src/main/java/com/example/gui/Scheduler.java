/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gui;

import java.util.List;

/**
 *
 * @author Mark
 */
public abstract class Scheduler {

    protected List<Job> jobs;
    private int currentTime;
    protected int avgWaititngTime;
    protected int avgTurnaroundTime;

    public Scheduler(List<Job> jobs) {
        this.jobs = jobs;
        this.currentTime = 0;
        this.avgTurnaroundTime = 0;
        this.avgWaititngTime = 0;

    }

    public int calculateAvgWaitingTime() {
        for (Job j : jobs) {
            avgWaititngTime = avgWaititngTime + j.getWaitingTime();
        }
        avgWaititngTime = avgWaititngTime / jobs.size();
        return avgWaititngTime;
    }

    public int calculateAvgTurnaroundTime() {
        for (Job j : jobs) {
            avgTurnaroundTime = avgTurnaroundTime + j.getTurnAroundTime();
        }
        avgTurnaroundTime = avgTurnaroundTime / jobs.size();
        return avgTurnaroundTime;

    }


    public void setAvgWaititngTime(int avgWaititngTime) {
        this.avgWaititngTime = avgWaititngTime;
    }

    public void setAvgTurnaroundTime(int avgTurnaroundTime) {
        this.avgTurnaroundTime = avgTurnaroundTime;
    }

    public int getAvgWaititngTime() {
        return avgWaititngTime;
    }

    public int getAvgTurnaroundTime() {
        return avgTurnaroundTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }


    protected abstract Job startScheduler(); // scheduler implementation here

    public Job schedule() {
        Job j = startScheduler();
        currentTime++;
        return j;
    }

    public final void enqueue(Job job) { // might be used to enqueue jobs in the list according to the used algorithm
        job.setArrivalTime(getCurrentTime());
        jobs.add(job);
    }
    public final void dequeue(Job job) { // might be used to enqueue jobs in the list according to the used algorithm
        job.setFinishTime(getCurrentTime() + 1);
        job.setWaitingTime(job.getFinishTime() - job.getArrivalTime() - job.getBurstTime());
        job.setTurnAroundTime(job.getFinishTime() - job.getArrivalTime());
        job.setStatus(Job.TERMINATED);
    }
    public boolean availToRun(Job job) {
        return job.getStatus() != Job.TERMINATED && job.getArrivalTime() <= currentTime;
    }
    public boolean notAvailToRun(Job job) {
        return !availToRun(job);
    }
}
