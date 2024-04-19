package com.example.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller1priority implements Initializable {
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
    private TableColumn<Job, Integer> priority_table;

    @FXML
    private TableColumn<Job, Integer> waiting_table;

    private Scheduler scheduler;
    @FXML
    private Label Timer;
    @FXML
    private TextField ArrivalTime;

    @FXML
    private Label AvgTurnaround;

    @FXML
    private Label AvgWaiting;
    private boolean allProcessesAdded = false;
    private int NoProccesses;

    private GanttChart<String, Long> ganttChart;


    @FXML
    void addProcess(ActionEvent event) {
        String name = processname.getText();
        int arrivalTime = Integer.parseInt(ArrivalTime.getText());
        int burstTime = Integer.parseInt(bursttime.getText());
        int priorityLevel = Integer.parseInt(priority.getText());

        NoProccesses++;



        /* for dynamic part*/
        if (NoProccesses>HelloController.num) {
            //addprocess.setDisable(true); // Disable the "Add Process" button
            Job newJob = new Job(name,0, burstTime,priorityLevel);
            scheduler.enqueue(newJob);
        }
        else {

            // Create a new Job object with the retrieved values
            Job newJob = new Job(name, arrivalTime, burstTime,priorityLevel);

            jobList.add(newJob);
        }
        allProcessesAdded = jobList.size() == HelloController.num;


        // Update the table
        updateTable();
    }

    Timeline timeline=null;
    @FXML
    void startSimulation(MouseEvent event) {
        if (!allProcessesAdded) {
            // Display a message to the user or handle the case where all processes haven't been added
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Incomplete Process List");
            alert.setContentText("Please add all processes before starting the simulation.");
            alert.showAndWait();
            return;
        }
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

        Job currJob = scheduler.schedule();
        if (currJob != null) {
            for (int i = 0; i < jobList.size(); i++) {
                if (jobList.get(i).getName().equals(currJob.getName())) {
                    jobList.set(i, currJob);
                    break;  // Stop after updating the first occurrence
                }

            }
            Timer.setText(Integer.toString(scheduler.getCurrentTime()));
            updateTable();
            return true;

        }else
        {
            AvgTurnaround.setText(String.valueOf(scheduler.calculateAvgTurnaroundTime()));
            AvgWaiting.setText(String.valueOf(scheduler.calculateAvgWaitingTime()));
            return false;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        switch (HelloController.scheduler) {
            case "PS Preemptive":
                scheduler = new PriorityPreemptive(jobList);
                break;
            case "PS Non Preemptive":
                scheduler = new priority_nonPreemptive(jobList);
                break;
            // Add cases for other scheduler types as needed
            default:
                // Default to SJF if the scheduler type is not recognized
                scheduler = new priority_nonPreemptive(jobList);
                break;
        }
        algorithmType.setText(HelloController.scheduler);
        Timer.setText(Integer.toString(scheduler.getCurrentTime()));

    }

    public void updateTable()
    {
        algorithmType.setText(HelloController.scheduler);

        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        burst_table.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        waiting_table.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        priority_table.setCellValueFactory(new PropertyValueFactory<>("priorityLevel"));
        Arrival_table.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));


        Table.setItems(jobList);
    }



}