package agh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtremeController implements Initializable {

    private Controller controller = Controller.getInstance();

    @FXML
    private Pane ExtremePane;
    @FXML
    private ListView<String> listView;
    @FXML
    private TableView tableView;
    @FXML
    private ChoiceBox choiceBox;

    @FXML
    protected void handleBack(ActionEvent event) {
        controller.handleBack(ExtremePane);
    }

    public void handleData(ActionEvent event) {
        if (choiceBox.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.ERROR, "Nie wybrano parametru").showAndWait();
            return;
        }
        String param = choiceBox.getSelectionModel().getSelectedItem().toString();
        controller.handleData(listView, "extreme," + param);
    }

    public void handleShowData(ActionEvent event) throws FileNotFoundException {
        controller.handleResults(tableView, "python/output/extreme.csv");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.initialize(listView);
    }

    public void setScene(Stage stage, Parent root) {
        controller.setScene(stage, root, "Extreme");
    }
}
