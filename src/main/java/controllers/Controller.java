package main.java.controllers;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import monitor.FileListener;
import org.javafxdata.datasources.provider.CSVDataSource;
import org.javafxdata.datasources.reader.DataSourceReader;
import org.javafxdata.datasources.reader.FileSource;
import main.Main;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static controllers.PredictController.readFile;

public class Controller implements FileListener {
    private static Controller controller = new Controller( );
    private volatile boolean changed = false;
    private volatile boolean isChanged = false;

    private Controller() { }

    /* Static 'instance' method */
    public static Controller getInstance( ) {
        return controller;
    }

    public void setScene(Stage stage, Parent root, String title){
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1140, 700));
        stage.show();
    }

    public void initialize(ListView listView) {
        List<String> values = new ArrayList<>();

        Set<String> colls = Main.database.getCollectionNames();
        values.addAll(colls);

        listView.setItems(FXCollections.observableList(values));
    }

    public void handleBack(Pane pane) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/main.fxml"));
        Parent root;
        try {
            root = (Parent) loader.load();
            MainController controller = (MainController) loader.getController();
            controller.setScene((Stage) pane.getScene().getWindow(), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleData(ListView<String> listView, String taskName) throws IOException {
        if (listView.getSelectionModel().getSelectedItem() == null){
            new Alert(Alert.AlertType.ERROR, "Nie wybrano danych").showAndWait();
            return;
        }

        String coll = listView.getSelectionModel().getSelectedItem();
        DBCollection collection = Main.database.getCollection(coll);
        DBCursor cursor = collection.find();
        JSON json = new JSON();
        String serialize = json.serialize(cursor);
        System.out.println(serialize);
        BufferedWriter out = new BufferedWriter(new FileWriter("python/input/classify.json"));
        out.write(serialize);
        out.close();
        BufferedWriter task = new BufferedWriter(new FileWriter("python/task_folder/task.txt"));
        task.write(taskName + "," + collection.count());
        task.close();
        isChanged = true;
    }

    public void handleResults(TableView tableView, String path) throws FileNotFoundException {
        if (!isChanged)
            return;
        isChanged = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Poczekaj na wykonanie analizy");
        while (!changed){
            alert.showAndWait();
        }
        alert.close();
        changed = false;
        DataSourceReader dsr1 = new FileSource(path);
        String[] columnsArray = {"m1", "m2", "m3", "m4", "m5", "t1", "cz1", "t2", "cz2", "ocena"};
        CSVDataSource ds1 = new CSVDataSource(dsr1,columnsArray);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(ds1.getData());
        tableView.getColumns().addAll(ds1.getColumns());
        TableColumn tableColumn = tableView.getVisibleLeafColumn(9);

        tableColumn.setCellFactory(column -> {
            return new TableCell<String, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<String> currentRow = getTableRow();

                    if (!isEmpty()) {
                        if(item.equals("v.good"))
                            currentRow.setStyle("-fx-background-color:lightgreen");
                        if(item.equals("v.bad"))
                            currentRow.setStyle("-fx-background-color:red");
                    }
                }
            };
        });
    }

    public void handleShowData(TextField textView, String path) throws IOException {
        if (!isChanged)
            return;
        isChanged = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Poczekaj na wykonanie analizy");
        while (!changed){
            alert.showAndWait();
        }
        alert.close();
        changed = false;
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = "";
        String[] result = null;
        while ((line = br.readLine()) != null)
            result = line.split(",");
        assert result != null;
        textView.setText(result[9]);
    }

    public void handleConfirm(){
        isChanged = true;
    }

    public void handleResult(String path, TextArea textArea) throws IOException {
        if (!isChanged)
            return;
        isChanged = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Poczekaj na wykonanie analizy");
        while (!changed){
            alert.showAndWait();
        }
        alert.close();
        String line;
        String content = "";
        FileReader fileReader = new FileReader(path);
        BufferedReader buffer = new BufferedReader(fileReader);

        while ((line = buffer.readLine()) != null) {
            content += line;
            content += "\n";
        }
        buffer.close();
        textArea.setText(content);
    }

    public void handleProcessing(String path) throws IOException {
        String line;
        String content = "";
        FileReader fileReader = new FileReader("python/output/processing.txt");
        BufferedReader buffer = new BufferedReader(fileReader);

        while ((line = buffer.readLine()) != null) {
            content += line;
            content += "\n";
        }
        buffer.close();
        content += "\nMachine Learning:\n";

        FileReader fileReader1 = new FileReader("python/output/process_machine_learning.txt");
        BufferedReader buffer1 = new BufferedReader(fileReader1);
        while ((line = buffer1.readLine()) != null) {
            content += line;
            content += "\n";
        }
        buffer1.close();

        TextArea textArea = new TextArea(content);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(textArea);

        Scene secondScene = new Scene(secondaryLayout, 1000, 700);

        Stage newWindow = new Stage();
        newWindow.setTitle("Processing");
        newWindow.setScene(secondScene);

        newWindow.show();
    }
    @Override
    public void fileChanged(File fileName){
        System.out.println("changed");
        changed = true;
    }
}
