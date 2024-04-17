package com.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    private ObservableList<Job> jobList = FXCollections.observableArrayList();
    @FXML
    private TableView<Job> Table;

    @FXML
    private Button addprocess;

    @FXML
    private Label algorithmType;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TableColumn<Job, Integer> burst_table;

    @FXML
    private TextField bursttime;

    @FXML
    private TableColumn<Job, String> name_table;

    @FXML
    private TextField processname;

    @FXML
    private TableColumn<Job, Integer> waiting_table;

    @FXML
    void addProcess(ActionEvent event) {
        String name = processname.getText();
        int arrivalTime = 0; // You need to get this value from the GUI as well
        int burstTime = Integer.parseInt(bursttime.getText());
        // Create a new Job object with the retrieved values
        Job newJob = new Job(name, arrivalTime, burstTime);

        jobList.add(newJob);
                /*System.out.print(newJob.getName());
                System.out.print(newJob.getArrivalTime());
                System.out.print(newJob.getPriorityLevel());
                System.out.print(newJob.getWaitingTime());
                System.out.print("      ");*/
        updateTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }
    public void updateTable()
    {
        algorithmType.setText(HelloController.scheduler);
        name_table.setCellValueFactory(new PropertyValueFactory<Job,String>("name"));
        burst_table.setCellValueFactory(new PropertyValueFactory<Job,Integer>("burstTime"));
        waiting_table.setCellValueFactory(new PropertyValueFactory<Job,Integer>("waiting"));

        Table.setItems(jobList);
    }

}
