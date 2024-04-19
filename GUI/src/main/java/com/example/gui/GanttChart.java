package com.example.gui;
import javafx.scene.text.Text;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GanttChart<X, Y> extends XYChart<X, Y> {
    private Map<String, Color> colorMap;
    private int currentXPosition = 0;

    public GanttChart() {
        this((Axis<X>) new CategoryAxis(), (Axis<Y>) new NumberAxis());
    }

    public GanttChart(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        colorMap = new HashMap<>();
        setData(FXCollections.observableArrayList()); // Initialize the data list
    }

    @Override
    protected void dataItemAdded(Series<X, Y> series, int i, Data<X, Y> data) {

    }

    @Override
    protected void dataItemRemoved(Data<X, Y> data, Series<X, Y> series) {

    }

    @Override
    protected void dataItemChanged(Data<X, Y> data) {

    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int i) {

    }

    @Override
    protected void seriesRemoved(Series<X, Y> series) {

    }




    public void addCurrentJob(String currentJob, int jobDuration) {
       double endPosition = currentXPosition + jobDuration;

        // Create a rectangle representing the current job
        Rectangle rect = new Rectangle(1, 10);
        rect.setFill(Color.BLUE); // Set color based on job name

        // Create a stack pane to hold the rectangle
        StackPane stackPane = new StackPane(rect);
        stackPane.setLayoutX(currentXPosition);

        // Create a new series for the current job
        Series<String, Number> series = new Series<>();

        // Add the data point representing the time of the current job to the series
        series.getData().add(new Data<>(currentJob, jobDuration));

        // Set the node for the data point to the stackPane
        series.getData().get(0).setNode(stackPane);

        // Add the series containing the job data to the chart's data
        getData().add((Series<X, Y>) series);

        // Update the current X position for the next job
        currentXPosition = (int) endPosition;


        // Get the current axis
       /* CategoryAxis xAxis = (CategoryAxis) getXAxis();

        // Calculate the position for the rectangle
        double startX = 0;
        double endX = currentXPosition + jobDuration;
        double width = jobDuration;
        double height = 10; // Fixed height

        // Create a rectangle
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.BLUE); // Set color based on job name

        // Position the rectangle in the middle of the category
        rectangle.setX(startX + (width / 2));
        rectangle.setY(getBoundsInLocal().getHeight() - height - 5); // Position above x-axis

        // Add the rectangle to the plot children for rendering
        getPlotChildren().add(rectangle);

        // Create text for the currentJob
        Text text = new Text(currentJob);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        text.setFill(Color.WHITE); // Set text color

        // Position the text in the middle of the rectangle
        text.setX(rectangle.getX() - (text.getLayoutBounds().getWidth() / 2));
        text.setY(rectangle.getY() + (rectangle.getHeight() / 2) + (text.getLayoutBounds().getHeight() / 4));

        // Add the text to the plot children for rendering
        getPlotChildren().add(text);*/
    }





    @Override
    protected void layoutPlotChildren() {

    }

    private Color generateColor() {
        // Generate a random color
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        return new Color(red, green, blue, 1);
    }



}
