package com.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private ComboBox<String> schedulers;

    @FXML
    void getScheduler(ContextMenuEvent event) {
        String scheduler = schedulers.getSelectionModel().getSelectedItem();
        System.out.println(scheduler);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulers.getItems().addAll("FCFS","SJF","RR","PS");

    }

}