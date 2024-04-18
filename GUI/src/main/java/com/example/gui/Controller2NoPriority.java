package com.example.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2NoPriority implements Initializable {
    @FXML
    private TableColumn<Job, String> ProcessesAdded;

    // @FXML
    // private TableView<Job> Table_processes;
    @FXML
    private Label algorithmType;
    @FXML
    private Label turnaroundField;
    @FXML
    private Label waitingField;
    @FXML
    private TableView<Job> Table;
    private ObservableList<Job> jobList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane anchor;
    @FXML
    private TableColumn<Job, Integer> Arrival_table;

    @FXML
    private TextField bursttime;

    @FXML
    private TextField priority;

    @FXML
    private TextField processname;

    @FXML
    private Button addprocess;
    @FXML
    private TableColumn<Job, Integer> burst_table;

    @FXML
    private TableColumn<Job, String> name_table;

    @FXML
    private TableColumn<Job, Integer> waiting_table;
    @FXML
    private static int NoProcesses;

    private Scheduler scheduler;

    @FXML
    void addProcess(ActionEvent event) {
        String name = processname.getText();
        int arrivalTime = 0; // You need to get this value from the GUI as well
        int burstTime = Integer.parseInt(bursttime.getText());
        // Create a new Job object with the retrieved values
        Job newJob = new Job(name, arrivalTime, burstTime);

        //jobList.add(newJob);

        scheduler.enqueue(newJob);
        //NoProcesses++;
        updateTable();

    }

    boolean state =true;
    Timeline timeline=null;
    @FXML
    void startSimulation(MouseEvent event) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            boolean hasMoreJobs = scheduling();
            if (!hasMoreJobs) {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public boolean scheduling() {

        Job currJob = scheduler.startScheduler();
        if (currJob != null) {
            for (int i = 0; i < jobList.size(); i++) {
                if (jobList.get(i).getName().equals(currJob.getName())) {
                    jobList.set(i, currJob);
                    break;  // Stop after updating the first occurrence
                }

            }
            updateTable();
            return true;

        }else
        {
            return false;
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        switch (HelloController.scheduler) {
            case "FCFS":
                scheduler = new FCFS(jobList);
                break;
            case "SJF Preemptive (SRTF)":
                scheduler = new SJF_Preemptive(jobList);
                break;
            case "SJF Non Preemptive":
                scheduler = new SJF_nonPreemptive(jobList);
                break;

            // Add cases for other scheduler types as needed
            default:
                // Default to SJF if the scheduler type is not recognized
                scheduler = new FCFS(jobList);
                break;
        }
        algorithmType.setText(HelloController.scheduler);
        //Timer.setText(Integer.toString(scheduler.getCurrentTime())); // doesn't work

    }

    public void updateTable()
    {
        //algorithmType.setText(HelloController.scheduler);
        //burst_table.setCellValueFactory(new PropertyValueFactory<Job,Integer>("burstTime"));
        //waiting_table.setCellValueFactory(new PropertyValueFactory<Job,Integer>("waiting"));
        //priority_table.setCellValueFactory(new PropertyValueFactory<Job,Integer>("priorityLevel"));


        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        burst_table.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        waiting_table.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        Arrival_table.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));


        Table.setItems(jobList);
    }



}