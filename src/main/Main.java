package main;

import com.mongodb.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monitor.FileMonitor;

import java.io.File;
import controllers.Controller;

public class Main extends Application {
    public static MongoClient mongoClient = new MongoClient();
    public static DB database = mongoClient.getDB("aghio");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1140, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        FileMonitor monitor = new FileMonitor (1000);

        monitor.addFile (new File("input/classify.csv"));
        monitor.addFile (new File("input/extreme.csv"));
        monitor.addFile (new File("input/predict.txt"));

        monitor.addListener (controller);
        //MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017"));
        /*MongoClient mongoClient = new MongoClient();
        DB database = mongoClient.getDB("aghio");
        //DBCollection collection = database.getCollection("production");

        for (int i=10; i<25; i++){
            DBCollection collection = database.getCollection("2018-01-" + i);
            for (int j=0; j<15; j++){
                DBObject prod = new BasicDBObject("_id", j)
                        .append("name", "stop niklu")
                        .append("parametry", new BasicDBObject("metale", new BasicDBObject("nikiel", 0.6)
                                .append("miedź", 0.2)
                                .append("żelazo", 0.1)
                                .append("mangan", 0.05)
                                .append("magnez", 0.05))
                                .append("temp1", ThreadLocalRandom.current().nextInt(1000, 1500 + 1))
                                .append("czas1", ThreadLocalRandom.current().nextInt(55, 75 + 1))
                                .append("temp2", ThreadLocalRandom.current().nextInt(800, 1100 + 1))
                                .append("czas2", ThreadLocalRandom.current().nextInt(25, 45 + 1)))
                        .append("jakość", ThreadLocalRandom.current().nextInt(1, 5 + 1));

                collection.insert(prod);
            }

        }
        Set<String> colls = database.getCollectionNames();

        for (String s : colls) {
            System.out.println(s);
        }*/


        launch(args);

    }
}
