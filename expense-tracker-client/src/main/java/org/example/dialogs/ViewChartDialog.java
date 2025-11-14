package org.example.dialogs;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.controllers.DashboardController;
import org.example.models.MonthlyFinance;

public class ViewChartDialog extends CustomDialog{
    public ViewChartDialog(DashboardController dashboardController , ObservableList<MonthlyFinance> monthlyFinances) {
        super(dashboardController);

        setTitle("View Chart");
        setWidth(700);
        setHeight(515);

        VBox barChartVbox = new VBox();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        xAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");
        yAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setMinWidth(getWidth() - 25);
        barChart.setMinHeight(getHeight()- 50);
        barChart.getStyleClass().add("text-size-md");


        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("income");
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("expense");


        // populated the data
        for (MonthlyFinance monthlyFinance : monthlyFinances){
            incomeSeries.getData().add(new XYChart.Data<>(monthlyFinance.getMonth(), monthlyFinance.getIncome()));
            expenseSeries.getData().add(new XYChart.Data<>(monthlyFinance.getMonth(), monthlyFinance.getExpense()));
        }
        barChart.getData().addAll(incomeSeries, expenseSeries);

        // update the bar colors for the income
        incomeSeries.getData().forEach(stringNumberData -> stringNumberData.getNode().setStyle("-fx-bar-fill: #13880d"));
        expenseSeries.getData().forEach(stringNumberData -> stringNumberData.getNode().setStyle("-fx-bar-fill: #ba2f2f"));

        barChartVbox.getChildren().add(barChart);

        getDialogPane().setContent(barChartVbox);
    }
}
