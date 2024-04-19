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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.example.gui.GanttChart;

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
    private StackPane ganttChartPane; // Reference to the StackPane in FXML

    @FXML
    private GanttChart<String, Number> ganttChart;


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

        // Clear previous data from GanttChart
        //ganttChart.getData().clear();

        // Start updating the Gantt Chart with each scheduling iteration
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
            // Add current job to Gantt Chart with its duration
            ganttChart.addCurrentJob(currJob.getName(), scheduler.getCurrentTime());
            return true;
        } else {
            AvgTurnaround.setText(String.valueOf(scheduler.calculateAvgTurnaroundTime()));
            AvgWaiting.setText(String.valueOf(scheduler.calculateAvgWaitingTime()));
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
            default:
                scheduler = new FCFS(jobList);
                break;
        }
        algorithmType.setText(HelloController.scheduler);
        initializeGanttChart(); // Initialize GanttChart
        //updateGanttChart();     // Update GanttChart after initialization
    }


    private void initializeGanttChart() {
        // Check if the ganttChartPane already contains the ganttChart
        if (!ganttChartPane.getChildren().contains(ganttChart)) {
            // Add GanttChart to the StackPane only if it's not already added
            ganttChartPane.getChildren().add(ganttChart);
        }
    }

    public void updateTable()
    {
        name_table.setCellValueFactory(new PropertyValueFactory<>("name"));
        burst_table.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        waiting_table.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        Arrival_table.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));


        Table.setItems(jobList);
        updateGanttChart();

    }
    private void updateGanttChart () {
        List<String> jobNames = new ArrayList<>();
        for (Job job : jobList) {
            jobNames.add(job.getName());
        }

        // Clear previous data from GanttChart
        //ganttChart.getData().clear();

        // Add new data to GanttChart using the list of job names
      //  ganttChart.addJob(jobNames);


}
}
