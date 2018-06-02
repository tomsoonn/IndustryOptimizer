package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable{

    private Controller controller = Controller.getInstance();

    @FXML private Pane StatsPane;
    @FXML private ListView<String> listView;
    @FXML private TextArea textArea;

    public void handleData(ActionEvent event) throws IOException {
        controller.handleData(listView, "statistics");
    }

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(StatsPane);
    }

    @FXML
    protected void handleResult(ActionEvent event) throws IOException {
        controller.handleResult("python/output/statistics.txt", textArea);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Stats");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.initialize(listView);
    }
}
