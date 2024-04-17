package com.example.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    @FXML
    private Label algorithmType;
    @FXML
    private Label turnaroundField;
    @FXML
    private Label waitingField;



    private ObservableList<Job> jobList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane anchor;

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

    /*    id.setCellValueFactory(new PropertyValueFactory<jobs,Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<jobs,String>("name"));
        lname.setCellValueFactory(new PropertyValueFactory<jobs,String>("lname"));
        gmail.setCellValueFactory(new PropertyValueFactory<jobs,String>("gmail"));
    */
    @FXML
    private TextField NoProcesses; // Remove the static keyword

    @FXML
    void addProcess(ActionEvent event) {
        // Get the number of jobs


            String name = processname.getText();
            int arrivalTime = 0; // You need to get this value from the GUI as well
            int burstTime = Integer.parseInt(bursttime.getText());
            // Create a new Job object with the retrieved values
            Job newJob = new Job(name, arrivalTime, burstTime);
            // Add the new job to your list of jobs or perform any other desired action
            // jobs.add(newJob); // Assuming you have a list of jobs

            name_table.setCellValueFactory(new PropertyValueFactory(newJob.getName()));
            burst_table.setCellValueFactory(cellData -> {
                int btime = cellData.getValue().getBurstTime();
                return new SimpleIntegerProperty(btime).asObject();
            });

            waiting_table.setCellValueFactory(cellData -> {
                int wtime = cellData.getValue().getWaitingTime();
                return new SimpleIntegerProperty(wtime).asObject();
            });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithmType.setText(HelloController.scheduler);
    }
}
