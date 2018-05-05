package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GeneratorController {
    public void setScene(Stage stage, Parent root){
        stage.setTitle("Generator");
        stage.setScene(new Scene(root, 1140, 700));
        stage.show();
    }

    @FXML
    private Pane GeneratorPane;

    @FXML
    protected void handleBack(ActionEvent event){
        System.out.println("Ala2");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/main.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            MainController controller = (MainController) loader.getController();
            controller.setScene((Stage) GeneratorPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
