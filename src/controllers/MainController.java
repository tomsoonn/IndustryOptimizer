package controllers;

import com.sun.tools.javah.Gen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private Pane mainPane;

    @FXML protected void handleClassify(ActionEvent event){

        System.out.println("Ala");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/classify.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            ClassifyControler controller = (ClassifyControler) loader.getController();
            controller.setScene((Stage) mainPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleExtreme(ActionEvent event){
        System.out.println("Ala1");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/extreme.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            ExtremeController controller = (ExtremeController) loader.getController();
            controller.setScene((Stage) mainPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handlePrediction(ActionEvent event){
        System.out.println("Ala2");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/predict.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            PredictController controller = (PredictController) loader.getController();
            controller.setScene((Stage) mainPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleStats(ActionEvent event){
        System.out.println("Ala3");
    }

    @FXML private Pane mainGridPane;

    @FXML protected void handleGenerateData(ActionEvent event){
        System.out.println("Ala4");
        mainGridPane.setVisible(true);
    }

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        System.out.println("Sign in");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/generator.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            GeneratorController controller = (GeneratorController) loader.getController();
            controller.setScene((Stage) mainPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setWindow(){

    }

    public void setScene(Stage stage, Parent root){
        stage.setTitle("Extreme");
        stage.setScene(new Scene(root, 1140, 700));
        stage.show();
    }
}
