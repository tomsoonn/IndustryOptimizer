package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class PredictController implements Initializable{

    private Controller controller = Controller.getInstance();

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
    private TextField quality;

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(PredictPane);
    }

    @FXML
    protected void handleConfirm(ActionEvent event) throws IOException {
        System.out.println(getValue());
        if (getValue() > 100){
            new Alert(Alert.AlertType.ERROR, "Niepoprawne wartości metali").showAndWait();
            return;
        }
        if (!hasValues()){
            new Alert(Alert.AlertType.ERROR, "Nie wprowadzono wszystkich danych").showAndWait();
            return;
        }
        String val = tf1.getText() + "," + tf2.getText() + "," + tf3.getText() + "," + tf4.getText() + "," + tf5.getText()
                + "," + tf6.getText() + "," + tf7.getText() + "," + tf8.getText() + "," + tf9.getText();
        BufferedWriter task = new BufferedWriter(new FileWriter("output/task.txt"));
        task.write("predict");
        task.close();
        BufferedWriter data = new BufferedWriter(new FileWriter("output/data.csv"));
        data.write(val);
        data.close();
    }

    @FXML
    protected void handleShowData(ActionEvent event) throws IOException {
        controller.handleShowData(quality, "input/predict.txt");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf1.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        tf2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf2.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        tf3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf3.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        tf4.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf4.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        tf5.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf5.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        tf6.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf6.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        tf7.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf7.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        tf8.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf8.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        tf9.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf9.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private double getValue(){
        double t1, t2, t3, t4, t5;
        if (tf1.getText() == null || tf1.getText().trim().isEmpty()) t1 = 0;
        else t1 = Double.parseDouble(tf1.getText());
        if (tf2.getText() == null || tf2.getText().trim().isEmpty()) t2 = 0;
        else t2 = Double.parseDouble(tf2.getText());
        if (tf3.getText() == null || tf3.getText().trim().isEmpty()) t3 = 0;
        else t3 = Double.parseDouble(tf3.getText());
        if (tf4.getText() == null || tf4.getText().trim().isEmpty()) t4 = 0;
        else t4 = Double.parseDouble(tf4.getText());
        if (tf5.getText() == null || tf5.getText().trim().isEmpty()) t5 = 0;
        else t5 = Double.parseDouble(tf5.getText());
        return t1 + t2 + t3 + t4 + t5;
    }

    private boolean hasValues(){
        if (tf1.getText() == null || tf1.getText().trim().isEmpty() || tf2.getText() == null || tf2.getText().trim().isEmpty() ||
                tf3.getText() == null || tf3.getText().trim().isEmpty() || tf4.getText() == null || tf4.getText().trim().isEmpty() ||
                tf5.getText() == null || tf5.getText().trim().isEmpty() || tf6.getText() == null || tf6.getText().trim().isEmpty() ||
                tf7.getText() == null || tf7.getText().trim().isEmpty() || tf8.getText() == null || tf8.getText().trim().isEmpty() ||
                tf9.getText() == null || tf9.getText().trim().isEmpty())
            return false;
        return true;
    }

    public void setScene(Stage stage, Parent root){
        stage.setTitle("Predict");
        stage.setScene(new Scene(root, 1140, 550));
        stage.show();
        //controller.setScene(stage, root, "Predict");
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
