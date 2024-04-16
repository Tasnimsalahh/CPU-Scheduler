package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private ComboBox<String> schedulers;
    @FXML
    private TextField NoProcesses;

    @FXML
    void getScheduler(ActionEvent event) {
        String scheduler = schedulers.getValue();
        System.out.println(scheduler);

    }
    @FXML
    void onNext(MouseEvent event) {
           String num=NoProcesses.getText();
           System.out.print(num);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulers.getItems().addAll("FCFS","SJF","RR","PS");
        schedulers.setOnAction(this::getScheduler);


    }

}