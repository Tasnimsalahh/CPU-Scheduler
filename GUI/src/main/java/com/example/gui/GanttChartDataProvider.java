package com.example.gui;

import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.*;

public class GanttChartDataProvider {
    private static final List<Color> colors = generateColors(20);
    private static final Map<String, Color> colorMap = new HashMap<>();

    private static List<Color> generateColors(int count) {
        List<Color> generatedColors = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double red = random.nextDouble();
            double green = random.nextDouble();
            double blue = random.nextDouble();
            Color color = new Color(red, green, blue, 1.0);
            generatedColors.add(color);
        }
        return generatedColors;
    }

    public static XYChart.Series<String, Long> generateGanttChartData(String processName, long duration) {
        XYChart.Series<String, Long> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(processName, duration));
        series.setName(processName);
        series.getData().get(0).setExtraValue(new ExtraData(duration, getNextColor(processName)));
        return series;
    }

    private static Color getNextColor(String processName) {
        if (!colorMap.containsKey(processName)) {
            colorMap.put(processName, colors.get(colorMap.size() % colors.size()));
        }
        return colorMap.get(processName);
    }

    public static class ExtraData {
        public long length;
        public Color color;

        public ExtraData(long length, Color color) {
            this.length = length;
            this.color = color;
        }

        public long getLength() {
            return length;
        }

        public Color getColor() {
            return color;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public String getStyleClass() {
            // You might want to implement your own logic to convert color to style class
            // For demonstration, returning a placeholder
            return "color-" + color.toString();
        }
    }
}
