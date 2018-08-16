package agh.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextField aluminium;
    @FXML
    private TextField krzem;
    @FXML
    private TextField magnez;
    @FXML
    private TextField miedz;
    @FXML
    private TextField cynk;
    @FXML
    private TextField cyna;
    @FXML
    private TextField nikiel;
    @FXML
    private TextField zelazo;
    @FXML
    private TextField olow;
    @FXML
    private TextField remained;
    @FXML
    private TextField quality;
    @FXML
    private ChoiceBox uprades;
    @FXML
    private ChoiceBox stops;
    @FXML
    private TextField temp;
    @FXML
    private TextField time;
    @FXML
    private TextField temp1;
    @FXML
    private TextField time1;
    @FXML
    private TextField temp2;
    @FXML
    private TextField time2;
    @FXML
    private TextField mass;
    @FXML
    private TextArea input;
    @FXML
    private TextField price;

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(PredictPane);
    }

    @FXML
    protected void handleConfirm(ActionEvent event) throws IOException {
        if (getValue() > 100){
            new Alert(Alert.AlertType.ERROR, "Niepoprawne wartości metali").showAndWait();
            return;
        }
//        String val = tf1.getText() + "," + tf2.getText() + "," + tf3.getText() + "," + tf4.getText() + "," + tf5.getText()
//                + "," + tf6.getText() + "," + tf7.getText() + "," + tf8.getText() + "," + tf9.getText();
        BufferedWriter task = new BufferedWriter(new FileWriter("python/task_folder/task.txt"));
        task.write("predict,1");
        task.close();
        BufferedWriter data = new BufferedWriter(new FileWriter("python/input/predict.csv"));
//        data.write(val + ",3");
        data.close();
        controller.handleConfirm();
    }

    @FXML
    protected void handleShowData(ActionEvent event) throws IOException {
        controller.handleShowData(quality, "python/output/results.csv");
    }

    public void handleProcessing(ActionEvent event) throws IOException {
        controller.handleProcessing("python/output/processing.txt");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Brak",
                        "Słabe",
                        "Srednie",
                        "Dobre",
                        "Bardzo Dobre"
                );
        uprades.setItems(options);
        options = FXCollections.observableArrayList(
                        "TODO"
                );
        stops.setItems(options);
        aluminium.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                aluminium.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        krzem.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                krzem.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        magnez.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                magnez.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        miedz.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                miedz.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        cynk.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cynk.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        cyna.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cyna.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        nikiel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                nikiel.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        zelazo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                zelazo.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });

        olow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                olow.setText(newValue.replaceAll("[^\\d]", ""));
            }
            remained.setText(String.valueOf(100 - getValue()));
        });
    }

    private double getValue(){
        double t1, t2, t3, t4, t5, t6, t7, t8, t9;
        if (aluminium.getText() == null || aluminium.getText().trim().isEmpty()) t1 = 0;
        else t1 = Double.parseDouble(aluminium.getText());
        if (krzem.getText() == null || krzem.getText().trim().isEmpty()) t2 = 0;
        else t2 = Double.parseDouble(krzem.getText());
        if (magnez.getText() == null || magnez.getText().trim().isEmpty()) t3 = 0;
        else t3 = Double.parseDouble(magnez.getText());
        if (miedz.getText() == null || miedz.getText().trim().isEmpty()) t4 = 0;
        else t4 = Double.parseDouble(miedz.getText());
        if (cynk.getText() == null || cynk.getText().trim().isEmpty()) t5 = 0;
        else t5 = Double.parseDouble(cynk.getText());
        if (cyna.getText() == null || cyna.getText().trim().isEmpty()) t6 = 0;
        else t6 = Double.parseDouble(cyna.getText());
        if (nikiel.getText() == null || nikiel.getText().trim().isEmpty()) t7 = 0;
        else t7 = Double.parseDouble(nikiel.getText());
        if (zelazo.getText() == null || zelazo.getText().trim().isEmpty()) t8 = 0;
        else t8 = Double.parseDouble(zelazo.getText());
        if (olow.getText() == null || olow.getText().trim().isEmpty()) t9 = 0;
        else t9 = Double.parseDouble(olow.getText());
        return t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8 + t9;
    }

    public void setScene(Stage stage, Parent root){
        stage.setTitle("Predict");
        stage.setScene(new Scene(root));
        stage.show();
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
