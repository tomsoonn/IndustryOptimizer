package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
        //if (filename.hasProperties() && quantity.hasProperties()){
            BufferedWriter task = new BufferedWriter(new FileWriter("output/task.txt"));
            task.write("generate;" + quantity.getText());
            task.close();
        //}
    }

    @FXML
    protected void handleShowData(ActionEvent event){

    }

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(GeneratorPane);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Generator");
    }
}
