package com.example.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2NoPriority implements Initializable {
    @FXML
    private Label algorithmType;
    @FXML
    private Label AvgTurnaround;

    @FXML
    private Label AvgWaiting;
    @FXML
    private TableView<Job> Table;
    private ObservableList<Job> jobList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane anchor;
    @FXML
    private TableColumn<Job, Integer> Arrival_table;
    @FXML
    private StackPane ganttChartPane;

    @FXML
    private TextField bursttime;

    @FXML
    private TextField processname;

    @FXML
    private Button addprocess;
    @FXML
    private TableColumn<Job, Integer> burst_table;

    @FXML
    private TableColumn<Job, String> name_table;
    @FXML
    private TextField ArrivalTime;

    @FXML
    private TableColumn<Job, Integer> waiting_table;
    @FXML
    private Label Timer;

    private boolean allProcessesAdded = false;
    private Scheduler scheduler;
    private  int NoProccesses;

    @FXML
    void addProcess(ActionEvent event) {
        String name = processname.getText();

        int burstTime = Integer.parseInt(bursttime.getText());
        int arrivalTime=Integer.parseInt(ArrivalTime.getText());
        NoProccesses++;



        /* for dynamic part*/
        if (NoProccesses>HelloController.num) {
            //addprocess.setDisable(true); // Disable the "Add Process" button
            Job newJob = new Job(name,0, burstTime);
            scheduler.enqueue(newJob);
        }
        else {

            // Create a new Job object with the retrieved values
            Job newJob = new Job(name, arrivalTime, burstTime);

            jobList.add(newJob);
        }

        allProcessesAdded = jobList.size() == HelloController.num;
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
        scheduler.setJobs(jobList);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            boolean hasMoreJobs = scheduling();
            if (!hasMoreJobs) {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    @FXML
    void startInstant(MouseEvent event) {
        if (!allProcessesAdded) {
            // Display a message to the user or handle the case where all processes haven't been added
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Incomplete Process List");
            alert.setContentText("Please add all processes before starting the simulation.");
            alert.showAndWait();
            return;
        }
        scheduler.setJobs(jobList);
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.0001), e -> {
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

        if (!scheduler.allProcessesTerminated()) {
            if(currJob!= null) {
                for (int i = 0; i < jobList.size(); i++) {
                    if (jobList.get(i).getName().equals(currJob.getName())) {
                        jobList.set(i, currJob);
                        break;  // Stop after updating the first occurrence
                    }
                }
            }

            Timer.setText(Integer.toString(scheduler.getCurrentTime()));
            updateGanttChart(currJob);
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
        //updateGanttChart(null);
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


    }
    static double rectX=-444;
    static double textX=-444;
    public void updateGanttChart(Job currJob) {

        double rectHeight = 125; // Fixed height for each rectangle
        double rectWidth = 25; // Fixed width for each rectangle

        // Update the rectangle for the current process if it's running
        Rectangle rect = new Rectangle(rectWidth, rectHeight, Color.AQUAMARINE);
        rect.setTranslateX(rectX);
        rect.setFill(Color.rgb(224, 145, 69));
        rect.setStroke(Color.BLACK);
        rectX += 25;
        Text text;
        if(currJob != null) {
             text = new Text(currJob.getName());
        }
        else {
            text = new Text("idle");

        }
        text.setFill(Color.BLACK);
        text.setTranslateX(textX);
        textX += 25;
        ganttChartPane.getChildren().addAll(rect, text);
    }

    public void updateTable()
    {
        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        burst_table.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        waiting_table.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        Arrival_table.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));


        Table.setItems(jobList);
    }

}