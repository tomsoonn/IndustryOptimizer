package controllers;

import com.mongodb.DBCursor;
import generator.Generator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratorController {

    private Controller controller = Controller.getInstance();

    @FXML
    private Pane GeneratorPane;
    @FXML
    private TextField filename;
    @FXML
    private TextField quantity;

    @FXML
    private void handleGenerate(ActionEvent event) throws IOException {
        if (filename.getText() == null || filename.getText().trim().isEmpty() || quantity.getText() == null || quantity.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Nie wprowadzono wszystkich danych").showAndWait();
            return;
        }
        Generator.generate(Integer.parseInt(quantity.getText()), filename.getText());
    }

    @FXML
    protected void handleShowData(ActionEvent event){
        long i = Main.database.getCollection(filename.getText()).count();
        System.out.println(i);
    }

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(GeneratorPane);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Generator");
    }
}
