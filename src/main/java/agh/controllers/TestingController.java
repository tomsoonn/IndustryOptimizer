package agh.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class TestingController implements Initializable {

    private Controller controller = Controller.getInstance();

    @FXML
    private ListView<String> listView;
    @FXML
    private Pane ClassifyPane;
    @FXML
    private TableView tableView;
    @FXML
    private ChoiceBox classifiers;

    @FXML
    protected void handleBack(ActionEvent event) {
        controller.handleBack(ClassifyPane);
    }

    public void handleResults(ActionEvent event) throws FileNotFoundException {
        controller.handleResults(tableView, "python/output/results.csv");
    }

    public void handleData(ActionEvent event) {
        controller.handleData(listView, "classify");
    }

    @FXML
    public void handleProcessing(ActionEvent event) {
        controller.handleProcessing(classifiers.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.initialize(listView);
        ObservableList<String> options = FXCollections.observableArrayList(
                "MultilayerPerceptron",
                "M5P",
                "RandomForest",
                "Vote"
        );
        classifiers.setItems(options);
        classifiers.getSelectionModel().selectFirst();
    }

    public void setScene(Stage stage, Parent root) {
        controller.setScene(stage, root, "Testowanie");
    }
}
