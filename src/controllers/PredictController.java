package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PredictController implements Initializable{

    int sum = 0;
    public void setScene(Stage stage, Parent root){
        stage.setTitle("Predict");
        stage.setScene(new Scene(root, 1140, 550));
        stage.show();
    }

    @FXML
    private Pane PredictPane;
    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;
    @FXML
    private TextField tf4;
    @FXML
    private TextField tf5;
    @FXML
    private TextField tf6;
    @FXML
    private TextField tf7;
    @FXML
    private TextField tf8;
    @FXML
    private TextField tf9;
    @FXML
    private TextField remained;

    @FXML
    protected void handleBack(ActionEvent event){
        System.out.println("Ala2");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("../fxml/main.fxml"));
        Parent root;
        try {
            root = (Parent)loader.load();
            MainController controller = (MainController) loader.getController();
            controller.setScene((Stage) PredictPane.getScene().getWindow(), root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleConfirm(ActionEvent event) throws IOException {
        String val = tf1.getText() + "," + tf2.getText() + "," + tf3.getText() + "," + tf4.getText() + "," + tf5.getText()
                + "," + tf6.getText() + "," + tf7.getText() + "," + tf8.getText() + "," + tf9.getText();
        BufferedWriter task = new BufferedWriter(new FileWriter("output/task.txt"));
        task.write("predict");
        task.close();
        BufferedWriter data = new BufferedWriter(new FileWriter("output/data.csv"));
        data.write(val);
        data.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf1.textProperty().addListener((observable, oldValue, newValue) -> {
            sum += Double.parseDouble(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            remained.setText(String.valueOf(100.0 - sum));
        });

        tf2.textProperty().addListener((observable, oldValue, newValue) -> {
            sum += Double.parseDouble(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            remained.setText(String.valueOf(100.0 - sum));
        });

        tf3.textProperty().addListener((observable, oldValue, newValue) -> {
            sum += Double.parseDouble(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            remained.setText(String.valueOf(100.0 - sum));
        });

        tf4.textProperty().addListener((observable, oldValue, newValue) -> {
            sum += Double.parseDouble(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            remained.setText(String.valueOf(100.0 - sum));
        });

        tf5.textProperty().addListener((observable, oldValue, newValue) -> {
            sum += Double.parseDouble(newValue);
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            remained.setText(String.valueOf(100.0 - sum));
        });
    }
}
