package controllers;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import generator.Generator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import org.bson.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GeneratorController {

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
    private void handleGenerate(ActionEvent event) throws IOException {
        if (filename.getText() == null || filename.getText().trim().isEmpty() || quantity.getText() == null || quantity.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Nie wprowadzono wszystkich danych").showAndWait();
            return;
        }
        Generator.generate(Integer.parseInt(quantity.getText()), filename.getText());
    }

    @FXML
    protected void handleShowData(ActionEvent event){
        DBCollection coll = Main.database.getCollection(filename.getText());
        DBCursor cursor = coll.find();
        JSON json = new JSON();
        String serialize = json.serialize(cursor);
        String area = "";
        String[] val = serialize.split("_");
        for (int i = 1; i<=Integer.parseInt(quantity.getText()); i++){
            String[] res = val[i].split("\"metale\" :");
            String[] res1 = res[1].split(", \"jakość\"");
            //System.out.println(res1[0]);
            area += res1[0];
            area += "\n";
            System.out.println(area);
        }
        textArea.clear();
        textArea.setText(area);
    }

    @FXML
    protected void handleBack(ActionEvent event){
        controller.handleBack(GeneratorPane);
    }

    public void setScene(Stage stage, Parent root){
        controller.setScene(stage, root, "Generator");
    }
}
