package agh;

import agh.generator.Generator;
import com.mongodb.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;

public class Main extends Application {
    public static MongoClient mongoClient = new MongoClient();
    public static DB database = mongoClient.getDB("aghio");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1140, 700));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Generator generator = new Generator();

        //minTime = 30, coeff = 2/3, minHeatingTime = 10, coolingTempCoeff2 = 0.5, minHeatTime2 = 25
        //generator.generateQuality(20, "AlSi.arff", "110000000", 30, 0.6666, 10, 0.5, 25,  90);
        //launch(args);
    }
}
