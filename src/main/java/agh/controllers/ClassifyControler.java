package agh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;


public class ClassifyControler implements Initializable {

    private Controller controller = Controller.getInstance();

    @FXML private ListView<String> listView;


    @FXML private Pane ClassifyPane;
    @FXML private TableView tableView;

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(ClassifyPane);
    }

    public void handleResults(ActionEvent event) throws FileNotFoundException {
        controller.handleResults(tableView,"python/output/results.csv");
    }

    public void handleData(ActionEvent event) throws IOException {
        controller.handleData(listView, "classify");
    }

    public void handleProcessing(ActionEvent event) throws IOException {
        controller.handleProcessing("python/output/processing.txt");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.initialize(listView);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Classify");
    }

}
