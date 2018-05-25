package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import monitor.FileListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ClassifyControler implements Initializable, FileListener {

    private Controller controller = Controller.getInstance();

    @FXML private ListView<String> listView;


    @FXML private Pane ClassifyPane;
    @FXML private TableView tableView;

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(ClassifyPane);
    }

    public void handleResults(ActionEvent event) throws FileNotFoundException {
        controller.handleResults(tableView,"input/result.csv");
    }

    public void handleData(ActionEvent event) throws IOException {
        controller.handleData(listView, "classify");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.initialize(listView);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Classify");
    }

    @Override
    public void fileChanged(File fileName) {

    }
}
