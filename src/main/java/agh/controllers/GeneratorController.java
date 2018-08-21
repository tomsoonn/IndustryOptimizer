package agh.controllers;

import agh.agents.InterfaceUI;
import agh.agents.MainContainer;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;
import agh.generator.Generator;
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
import javafx.stage.Stage;
import agh.Main;
import org.codehaus.jackson.io.UTF8Writer;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

public class GeneratorController implements Initializable {

    private Controller controller = Controller.getInstance();

    @FXML
    private Pane GeneratorPane;
    @FXML
    private TextField filename;
    @FXML
    private TextField quantity;
    @FXML
    private TextArea textArea;
    @FXML
    private CheckBox unlabeled;
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
    private ChoiceBox<String> stops;
    @FXML
    private Button addToDb;

    @FXML
    private void handleGenerate(ActionEvent event) {
        checkIfProperData();
        if (filename.getText() == null || filename.getText().trim().isEmpty() || quantity.getText() == null || quantity.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Nie wprowadzono wszystkich danych").showAndWait();
            return;
        }
        addToDb.setDisable(unlabeled.isSelected());
        int[] data = {
                Integer.parseInt(aluminium.getText()),
                Integer.parseInt(krzem.getText()),
                Integer.parseInt(magnez.getText()),
                Integer.parseInt(miedz.getText()),
                Integer.parseInt(cynk.getText()),
                Integer.parseInt(cyna.getText()),
                Integer.parseInt(nikiel.getText()),
                Integer.parseInt(zelazo.getText()),
                Integer.parseInt(olow.getText()),
        };
        Generator.generateQ(filename.getText(), data, Integer.parseInt(quantity.getText()), !unlabeled.isSelected(), true);
    }

    @FXML
    private void handelAddToDb(ActionEvent event) throws ControllerException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy plik zawiera POPRAWNE dane treningowe?");
        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent())
            return;
        else if (result.get() == ButtonType.OK) {
            addToDataBase();
        } else if (result.get() == ButtonType.CANCEL)
            return;
    }

    private void addToDataBase() throws ControllerException {
        //TODO

        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(filename.getText()));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot open file").showAndWait();
        }
        String data = new String(encoded, StandardCharsets.UTF_8).split("@DATA")[1];
        FileWriter file;
        try {
            file = new FileWriter("TrainingData.arff", true);

        BufferedWriter out = new BufferedWriter(file);
        out.write(data);
        out.close();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot find training file").showAndWait();
        }

        AgentController ac = MainContainer.cc.getAgent("UI-agent");
        InterfaceUI uiObj = ac.getO2AInterface(InterfaceUI.class);

        uiObj.startTraining();
    }

    private void checkIfProperData(){
        //TODO
    }

    @FXML
    protected void handleShowData(ActionEvent event) {
        //DBCollection coll = Main.database.getCollection(filename.getText());
        //DBCursor cursor = coll.find();
        //JSON json = new JSON();
        //String serialize = json.serialize(cursor);
        //String area = "";
        //String[] val = serialize.split("_");
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(filename.getText()));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot open file").showAndWait();
        }
        String area = new String(encoded, StandardCharsets.UTF_8);
//        for (int i = 1; i<=Integer.parseInt(quantity.getText()); i++){
//            String[] res = val[i].split("\"metale\" :");
//            String[] res1 = res[1].split(", \"jakość\"");
//            area += res1[0];
//            area += "\n";
//        }
        textArea.clear();
        textArea.setText(area);
    }

    @FXML
    protected void handleBack(ActionEvent event) {
        controller.handleBack(GeneratorPane);
    }

    public void setScene(Stage stage, Parent root) {
        controller.setScene(stage, root, "Generator");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeChoiceBoxes();
        initializeTextFields();

    }

    private void initializeChoiceBoxes() {
        ObservableList<String> options = FXCollections.observableArrayList(
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
        stops.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setStops(newValue.intValue());
            }
        });

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
}
