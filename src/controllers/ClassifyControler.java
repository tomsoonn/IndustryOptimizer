package controllers;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.javafxdata.datasources.provider.CSVDataSource;
import org.javafxdata.datasources.reader.DataSourceReader;
import org.javafxdata.datasources.reader.FileSource;

import javax.swing.text.TabableView;

public class ClassifyControler implements Initializable{

    MongoClient mongoClient = new MongoClient();
    DB database = mongoClient.getDB("aghio");

    @FXML private ListView<String> listView;

    public void setScene(Stage stage, Parent root){
        stage.setTitle("Classify");
        stage.setScene(new Scene(root, 1140, 700));
        stage.show();
    }

    @FXML private Pane ClassifyPane;
    @FXML private TableView tableView;

    @FXML
    protected void handleBack(ActionEvent event){
        System.out.println("Ala2");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/main.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            MainController controller = (MainController) loader.getController();
            controller.setScene((Stage) ClassifyPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleResults(ActionEvent event) throws FileNotFoundException {
        DataSourceReader dsr1 = new FileSource("input/dane2-2.csv");
        String[] columnsArray = {"m1", "m2", "m3", "m4", "m5", "t1", "cz1", "t2", "cz2", "ocena"};
        CSVDataSource ds1 = new CSVDataSource(dsr1,columnsArray);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(ds1.getData());
        tableView.getColumns().addAll(ds1.getColumns());
        TableColumn tableColumn = tableView.getVisibleLeafColumn(9);

        tableColumn.setCellFactory(column -> {
            return new TableCell<String, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<String> currentRow = getTableRow();

                    if (!isEmpty()) {

                        if(item.equals("v.good"))
                            currentRow.setStyle("-fx-background-color:lightgreen");
                        //else if(item.equals("v.bad"))
                        //  currentRow.setStyle("-fx-background-color:lightcoral");
                    }
                }
            };
        });
    }

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
        task.write("classify");
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
