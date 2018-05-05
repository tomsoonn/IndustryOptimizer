package controllers;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ExtremeController implements Initializable{
    public void setScene(Stage stage, Parent root){
        stage.setTitle("Extreme");
        stage.setScene(new Scene(root, 1140, 700));
        stage.show();
    }

    @FXML private Pane ExtremePane;
    @FXML private ListView<String> listView;

    MongoClient mongoClient = new MongoClient();
    DB database = mongoClient.getDB("aghio");

    @FXML
    protected void handleBack(ActionEvent event){
        System.out.println("Ala2");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/main.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            MainController controller = (MainController) loader.getController();
            controller.setScene((Stage) ExtremePane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleData(ActionEvent event) throws IOException {
        String coll = listView.getSelectionModel().getSelectedItem();
        DBCollection collection = database.getCollection(coll);
        DBCursor cursor = collection.find();
        JSON json = new JSON();
        String serialize = json.serialize(cursor);
        System.out.println(serialize);
        BufferedWriter out = new BufferedWriter(new FileWriter("output/test.json"));
        out.write(serialize);
        out.close();
        BufferedWriter task = new BufferedWriter(new FileWriter("output/task.txt"));
        task.write("extreme");
        task.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> values = new ArrayList<>();

        Set<String> colls = database.getCollectionNames();
        values.addAll(colls);

        listView.setItems(FXCollections.observableList(values));
    }
}
