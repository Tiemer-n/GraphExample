/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stocksandshares;



import java.awt.Color;
import java.io.IOException;
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Isaac
 */
public class StocksAndShares extends Application {

    /**
     * @param args the command line arguments
     */
    private ScheduledExecutorService scheduledExecutorService;
    final int WINDOW_SIZE = 10;
    
    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setAnimated(false); // axis animations are removed
        final LineChart<String, Number> lineChart = 
                new LineChart<String, Number>(xAxis,yAxis);
                
        lineChart.setTitle("Stock Monitoring");
                                
        XYChart.Series series = new XYChart.Series();
        series.setName("Stocks");
        XYChart.Series test = new XYChart.Series();
        test.setName("Stocks2");
         XYChart.Series another = new XYChart.Series();
        another.setName("Stocks23");
        Color colour = Color.RED;
        lineChart.setAnimated(false); // disable animations
        lineChart.setCreateSymbols(false);
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series,test,another);
       
        stage.setScene(scene);
        stage.show();
        
        
        //adding a scheduledExecutorService to display time in HH:MM:ss
        final SimpleDateFormat DataFormat = new SimpleDateFormat("HH:mm:ss");
        
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        
        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10
            final double random = setnumber();
            final double random2 = setnumberPositive();
            final double random3 = setnumberNegative();
            
            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();
                // put random number with current time
                series.getData().add(new XYChart.Data<>(DataFormat.format(now), random));
                test.getData().add(new XYChart.Data<>(DataFormat.format(now), random2));
                another.getData().add(new XYChart.Data<>(DataFormat.format(now), random3));
                
                if (series.getData().size() > WINDOW_SIZE){
                    series.getData().remove(0);
                    test.getData().remove(0);
                    another.getData().remove(0);
                }
                    
            });
        }, 0, 1000, TimeUnit.MILLISECONDS);
        
        
        
    }
    
    public double setnumber(){
        Random rand = new Random();
        double random = ThreadLocalRandom.current().nextDouble(2.000);
        
        final int isNegative = rand.nextInt(2);
        if(isNegative == 1){
            random = -random;
        }
        
        return random;
    }
    public double setnumberPositive(){
        Random rand = new Random();
        double random = ThreadLocalRandom.current().nextDouble(4.000);
        random+=2;
        final int isNegative = rand.nextInt(2);
//        if(isNegative == 1){
//            random = -random;
//        }
        
        return random;
    }
    public double setnumberNegative(){
        Random rand = new Random();
        double random = ThreadLocalRandom.current().nextDouble(4.000);
        random+=2;
        random = -random;
        final int isNegative = rand.nextInt(2);
//        if(isNegative == 1){
//            random = -random;
//        }
        
        return random;
    }
    
    
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
    public static void main(String[] args) {
        launch(args);
    }

    
    
    
    
    
    
}
