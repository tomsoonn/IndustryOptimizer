package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable{

    private Controller controller = Controller.getInstance();

    @FXML private Pane StatsPane;
    @FXML private ListView<String> listView;

    public void handleData(ActionEvent event) throws IOException {
        controller.handleData(listView, "stats");
    }

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(StatsPane);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Stats");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.initialize(listView);
    }
}
