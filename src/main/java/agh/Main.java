package agh;

import agh.classification.ProductionData;
import agh.agents.MainContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
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

    public static void main(String[] args) {

        try {
            AgentController prod = MainContainer.cc.createNewAgent("Production-agent",
                    "agh.agents.ProductionAgent", null);
            prod.start();
            AgentController rma = MainContainer.cc.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() ->
                productionData.train("TrainingData.arff"));
        thread.start();
        launch(args);
    }
}
