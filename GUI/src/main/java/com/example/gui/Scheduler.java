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
    protected int currentTime;
    protected int avgWaititngTime;
    protected int avgTurnaroundTime;
    protected int schedulerStepCounter; // Counts how many times the startScheduler method was called

    public Scheduler(List<Job> jobs) {
        this.jobs = jobs;
        this.currentTime = 0;
        this.avgTurnaroundTime = 0;
        this.avgWaititngTime = 0;
        this.schedulerStepCounter = 0;
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

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
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

    public Job schedule() { // called by the GUI
        schedulerStepCounter++;
        return startScheduler();
    }

    public abstract void enqueue(Job job); // might be used to enqueue jobs in the list according to the used algorithm

    public abstract void dequeue(Job job); // might be used to enqueue jobs in the list according to the used algorithm

}
