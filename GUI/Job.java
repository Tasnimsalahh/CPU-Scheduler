/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo2;

/**
 *
 * @author Mark
 */
public class Job {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int startTime;
    private int finishTime;
    private int priorityLevel;
    private int remainingTime;
    private int waitingTime;
    private int turnaroundTime;
    public Job(String name, int arrivalTime, int burstTime, int priorityLevel) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityLevel = priorityLevel;
        this.remainingTime = this.burstTime;
    }

    public Job(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnaroundTime = turnAroundTime;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
        
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnAroundTime() {
        return turnaroundTime;
    }
    
    
 
}
