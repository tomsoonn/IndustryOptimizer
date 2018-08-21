package agh;

import agh.classification.ProductionData;
import agh.agents.MainContainer;
import agh.generator.Generator;
import com.mongodb.*;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
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
    public static ProductionData productionData = new ProductionData();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("IndustryOptimizer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            AgentController prod = MainContainer.cc.createNewAgent("Production-agent",
                    "agh.agents.ProductionAgent", null);
            prod.start();
            AgentController rma = MainContainer.cc.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        //Generator generator = new Generator();

        //minTime = 30, coeff = 2/3, minHeatingTime = 10, coolingTempCoeff2 = 0.5, minHeatTime2 = 25
        //generator.generateQuality(20, "AlSi.arff", "110000000", 30, 0.6666, 10, 0.5, 25,  90);

        Thread thread = new Thread(() ->
                productionData.train("TrainingData.arff"));
        thread.start();
        launch(args);
    }
}
