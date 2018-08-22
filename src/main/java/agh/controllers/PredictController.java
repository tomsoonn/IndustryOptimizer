package agh.controllers;

import agh.agents.InterfaceUI;
import agh.agents.MainContainer;
import agh.classification.WekaManager;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class PredictController implements Initializable {

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
    private ChoiceBox<String> upgrades;
    @FXML
    private ChoiceBox<String> stops;
    @FXML
    private ChoiceBox<String> classifiers;
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
    private TextField cost;

    @FXML
    protected void handleBack(ActionEvent event) {
        controller.handleBack(PredictPane);
    }

    private void checkIfProperData() {
        if (getValue() > 100) {
            new Alert(Alert.AlertType.ERROR, "Niepoprawne wartości metali").showAndWait();
            return;
        }
//        String val = tf1.getText() + "," + tf2.getText() + "," + tf3.getText() + "," + tf4.getText() + "," + tf5.getText()
//                + "," + tf6.getText() + "," + tf7.getText() + "," + tf8.getText() + "," + tf9.getText();

        controller.handleConfirm();
    }

    @FXML
    public void handleProcessing(ActionEvent event) throws IOException {
        controller.handleProcessing(classifiers.getSelectionModel().getSelectedIndex());
    }

    @FXML
    void handlePrediction() throws ControllerException {
        checkIfProperData();
        String[] data = {
                aluminium.getText(),
                krzem.getText(),
                magnez.getText(),
                miedz.getText(),
                cynk.getText(),
                cyna.getText(),
                nikiel.getText(),
                zelazo.getText(),
                olow.getText(),
                temp.getText(),
                time.getText(),
                temp1.getText(),
                time1.getText(),
                temp2.getText(),
                time2.getText(),
                Integer.toString(upgrades.getSelectionModel().getSelectedIndex() + 1),
                mass.getText()

        };

        AgentController ac = MainContainer.cc.getAgent("UI-agent");
        InterfaceUI uiObj = ac.getO2AInterface(InterfaceUI.class);

        String result = uiObj.runProcess(data, classifiers.getSelectionModel().getSelectedIndex());
        String[] results = result.split("-");
        input.setText(results[0]);
        cost.setText(results[1]);
        quality.setText(results[2]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeChoiceBoxes();
        initializeTextFields();

        initializeAllParameters(); //TEMPORARY FOR TESTING
    }

    private void initializeAllParameters() {
        stops.getSelectionModel().select(2);
        temp.setText("1500");
        time.setText("50");
        temp1.setText("1000");
        time1.setText("15");
        temp2.setText("700");
        time2.setText("25");
        mass.setText("100");
    }

    private void initializeChoiceBoxes() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Brak",
                        "Słabe",
                        "Srednie",
                        "Dobre",
                        "Bardzo Dobre"
                );
        upgrades.setItems(options);
        upgrades.getSelectionModel().selectFirst();
        options = FXCollections.observableArrayList(
                "AlSi",
                "AlSiMg",
                "AlSiCuMg",
                "MgAlSi",
                "MgAlZn",
                "CuSn-brąz",
                "CuSn-mosiądz",
                "NiCuFe",
                "PbSnCu"
        );
        stops.setItems(options);
        stops.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> setStops(newValue.intValue()));
        options = FXCollections.observableArrayList(
                "MultilayerPerceptron",
                "M5P",
                "RandomForest",
                "Vote"
        );
        classifiers.setItems(options);
        classifiers.getSelectionModel().selectFirst();
    }

    private void initializeTextFields() {
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

    private double getValue() {
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

    public void setScene(Stage stage, Parent root) {
        stage.setTitle("Predict");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void setStops(int i) {

        int[] alsi = {80, 20, 0, 0, 0, 0, 0, 0, 0};
        int[] alsimg = {80, 15, 5, 0, 0, 0, 0, 0, 0};
        int[] alsimgcu = {80, 12, 2, 6, 0, 0, 0, 0, 0};
        int[] mgalsi = {8, 2, 90, 0, 0, 0, 0, 0, 0};
        int[] mgalzn = {7, 0, 90, 0, 3, 0, 0, 0, 0};
        int[] cusn = {0, 0, 0, 98, 0, 2, 0, 0, 0};
        int[] cuzn = {0, 0, 0, 70, 30, 0, 0, 0, 0};
        int[] nicufe = {0, 0, 0, 30, 0, 0, 67, 3, 0};
        int[] pbzncu = {0, 0, 0, 2, 0, 3, 0, 0, 95};

        switch (i) {
            case 0:
                setTextFields(alsi);
                break;
            case 1:
                setTextFields(alsimg);
                break;
            case 2:
                setTextFields(alsimgcu);
                break;
            case 3:
                setTextFields(mgalsi);
                break;
            case 4:
                setTextFields(mgalzn);
                break;
            case 5:
                setTextFields(cusn);
                break;
            case 6:
                setTextFields(cuzn);
                break;
            case 7:
                setTextFields(nicufe);
                break;
            case 8:
                setTextFields(pbzncu);
                break;
        }

    }

    private void setTextFields(int[] metals) {
        aluminium.setText(Integer.toString(metals[0]));
        krzem.setText(Integer.toString(metals[1]));
        magnez.setText(Integer.toString(metals[2]));
        miedz.setText(Integer.toString(metals[3]));
        cynk.setText(Integer.toString(metals[4]));
        cyna.setText(Integer.toString(metals[5]));
        nikiel.setText(Integer.toString(metals[6]));
        zelazo.setText(Integer.toString(metals[7]));
        olow.setText(Integer.toString(metals[8]));
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
