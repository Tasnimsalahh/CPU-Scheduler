package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Pane anchor;
    @FXML
    private ComboBox<String> schedulers;
    @FXML
    private TextField NoProcesses;
    protected static int num=-1;
    @FXML
    private Button onNext;

    protected static String scheduler=null;


    @FXML
    void getScheduler(ActionEvent event) {
        scheduler = schedulers.getValue();
    }
    @FXML
    void NoProcesses(KeyEvent event) {
        num = Integer.parseInt (NoProcesses.getText());
    }
    @FXML
    void onNext(MouseEvent event) throws IOException {
        if (scheduler==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No scheduler added");
            alert.setContentText("Please select scheduler.");
            alert.showAndWait();
            return;
        }
        if (num==-1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No processes added");
            alert.setContentText("Please add No processes.");
            alert.showAndWait();
            return;
        }

        if (scheduler.equals( "PS Preemptive") ||scheduler.equals("PS Non Preemptive")) {
            Stage stage = (Stage) onNext.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("priority.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Priority schedulers");
            stage.setScene(scene);


        }else if(scheduler.equals("RR")){
            Stage stage = (Stage) onNext.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RR.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Round Robin");
            stage.setScene(scene);
        }
        else {

            Stage stage = (Stage) onNext.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("noPriority.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Non-Priority schedulers");
            stage.setScene(scene);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulers.getItems().addAll("FCFS", "SJF Preemptive (SRTF)","SJF Non Preemptive", "RR", "PS Preemptive","PS Non Preemptive");
        schedulers.setOnAction(this::getScheduler);
    }
}
