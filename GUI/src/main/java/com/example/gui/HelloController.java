package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        System.out.println(scheduler);

    }
    @FXML
    void onNext(MouseEvent event) throws IOException {
           String num=NoProcesses.getText();
           System.out.print(num);

           Stage stage = (Stage) onNext.getScene().getWindow();
           FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sample1.fxml"));
           Scene scene = new Scene(fxmlLoader.load(), 700, 700);
           stage.setTitle("Stage 2");
           stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulers.getItems().addAll("FCFS","SJF","RR","PS");
        schedulers.setOnAction(this::getScheduler);


    }

}