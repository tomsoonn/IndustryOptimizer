package main;

import com.mongodb.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monitor.FileMonitor;

import java.io.*;
import java.util.Properties;

import controllers.Controller;

public class Main extends Application {
    public static MongoClient mongoClient = new MongoClient();
    public static DB database = mongoClient.getDB("aghio");
    private static Process process;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1140, 700));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                process.destroy();
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);

        ProcessBuilder pb = new ProcessBuilder(prop.getProperty("pythonPath"), "main.py");
        pb.directory(new File("./python"));
        process = pb.start();


        Controller controller = Controller.getInstance();
        FileMonitor monitor = new FileMonitor (1000);

        monitor.addFile (new File("python/output/results.csv"));
        monitor.addFile (new File("python/output/extreme.csv"));
        monitor.addFile (new File("python/output/statistics.txt"));
        monitor.addListener (controller);

        launch(args);
    }
}
