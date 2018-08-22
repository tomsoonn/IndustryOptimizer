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
import java.util.*;

public class GeneratorController implements Initializable {

    private Controller controller = Controller.getInstance();
    private Map<Integer, int[]> metalsMap = new HashMap<>();
    private TextField[] metalsArray;

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
    private TextField stopName;

    @FXML
    private void handleGenerate(ActionEvent event) {
        checkIfProperData();
        if (filename.getText() == null || filename.getText().trim().isEmpty() || quantity.getText() == null || quantity.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Nie wprowadzono wszystkich danych").showAndWait();
            return;
        }
        addToDb.setDisable(unlabeled.isSelected());
        int[] data = getMetalsPercentage();
        Generator.generateQ(filename.getText(), data, Integer.parseInt(quantity.getText()), !unlabeled.isSelected(), true);
    }

    @FXML
    private void handelAddToDb(ActionEvent event) throws ControllerException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy plik zawiera POPRAWNE dane treningowe?");
        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
        } else if (result.get() == ButtonType.OK) {
            addToDataBase();
        } else if (result.get() == ButtonType.CANCEL) {
        }
    }

    @FXML
    private void handleAddStop(ActionEvent event) {
        String name = stopName.getText();
        int[] data = getMetalsPercentage();
        //TODO
    }

    private void addToDataBase() throws ControllerException {

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

    private void checkIfProperData() {
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

    private void initTextFieldsArray(){
        metalsArray = new TextField[]{aluminium, krzem, magnez, miedz, cynk, cyna, nikiel, zelazo, olow};
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
        stops.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> setStops(newValue.intValue()));

    }

    private void initializeTextFields() {
        initMetalsMap();
        initTextFieldsArray();
        for (int i=0; i<metalsArray.length; i++){
            TextField metal = metalsArray[i];
            metal.textProperty().addListener((observable, oldValue, newValue) -> {
                remained.setText(String.valueOf(100 - getValue()));
            });
        }
    }

    private double getValue() {
        double sum = 0;
        for (int i=0; i<metalsArray.length; i++)
            if (metalsArray[i].getText() != null && !metalsArray[i].getText().trim().isEmpty())
                sum += Double.parseDouble(metalsArray[i].getText());
        return sum;
    }

    private void initMetalsMap(){
        metalsMap.put(0, new int[]{80, 20, 0, 0, 0, 0, 0, 0, 0});
        metalsMap.put(1, new int[]{80, 15, 5, 0, 0, 0, 0, 0, 0});
        metalsMap.put(2, new int[]{80, 12, 2, 6, 0, 0, 0, 0, 0});
        metalsMap.put(3, new int[]{8, 2, 90, 0, 0, 0, 0, 0, 0});
        metalsMap.put(4, new int[]{7, 0, 90, 0, 3, 0, 0, 0, 0});
        metalsMap.put(5, new int[]{0, 0, 0, 98, 0, 2, 0, 0, 0});
        metalsMap.put(6, new int[]{0, 0, 0, 70, 30, 0, 0, 0, 0});
        metalsMap.put(7, new int[]{0, 0, 0, 30, 0, 0, 67, 3, 0});
        metalsMap.put(8, new int[]{0, 0, 0, 2, 0, 3, 0, 0, 95});
    }

    private void addToMetalsMap(){

    }

    private void setStops(int i) {
        setTextFields(metalsMap.get(i));
    }

    private void setTextFields(int[] metals) {
        for (int i=0; i<metalsArray.length; i++)
            metalsArray[i].setText(Integer.toString(metals[i]));
    }

    private int[] getMetalsPercentage() {
        int[] data = new int[metalsArray.length];
        for (int i=0; i<metalsArray.length; i++)
            data[i] = Integer.parseInt(metalsArray[i].getText());
        return data;
    }

}
