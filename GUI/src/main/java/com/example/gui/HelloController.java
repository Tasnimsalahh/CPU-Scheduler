package com.example.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private ComboBox<String> schedulers;
    @FXML
    private TextField NoProcesses;
    @FXML
    private Button onNext;

    protected static String scheduler;


    @FXML
    void getScheduler(ActionEvent event) {
        scheduler = schedulers.getValue();
        // Controller1.algorithmType.setText(scheduler);
        //System.out.println(scheduler);
    }

    @FXML
    void onNext(MouseEvent event) throws IOException {
        String num = NoProcesses.getText();
        System.out.print(num);
        if (scheduler.equals( "PS Preemptive") ||scheduler.equals("PS Non Preemptive")) {
            Stage stage = (Stage) onNext.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("priority.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 700);
            stage.setTitle("Stage 2");
            stage.setScene(scene);


        }else if(scheduler.equals("RR")){
            Stage stage = (Stage) onNext.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RR.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 700);
            stage.setTitle("Stage 4");
            stage.setScene(scene);
        }
        else {

            Stage stage = (Stage) onNext.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("noPriority.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 700);
            stage.setTitle("Stage 3");
            stage.setScene(scene);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulers.getItems().addAll("FCFS", "SJF Preemptive (SRTF)","SJF Non Preemptive", "RR", "PS Preemptive","PS Non Preemptive");
        schedulers.setOnAction(this::getScheduler);
    }
}
