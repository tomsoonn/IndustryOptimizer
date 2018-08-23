package agh.controllers;

import agh.classification.WekaManager;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.javafxdata.datasources.provider.CSVDataSource;
import org.javafxdata.datasources.reader.DataSourceReader;
import org.javafxdata.datasources.reader.FileSource;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import agh.Main;

public class Controller {
    private static Controller controller = new Controller();
    private volatile boolean changed = false;
    private volatile boolean isChanged = false;

    private Controller() {
    }

    /* Static 'instance' method */
    public static Controller getInstance() {
        return controller;
    }

    public void setScene(Stage stage, Parent root, String title) {
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize(ListView listView) {
        List<String> values = new ArrayList<>();
        File[] listOfFiles = new File(".").listFiles((dir, name) -> name.endsWith(".arff"));

        if (listOfFiles != null && listOfFiles.length > 0) {
            Arrays.sort(listOfFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    values.add(listOfFile.getName());
                }
            }
        }

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

    public void handleData(ListView<String> listView, String taskName) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.ERROR, "Nie wybrano danych").showAndWait();
            return;
        }

        String coll = listView.getSelectionModel().getSelectedItem();
        //TODO
    }

    public void handleResults(TableView tableView, String path) throws FileNotFoundException {
        DataSourceReader dsr1 = new FileSource(path);
        String[] columnsArray = {"m1", "m2", "m3", "m4", "m5", "t1", "cz1", "t2", "cz2", "ocena"};
        CSVDataSource ds1 = new CSVDataSource(dsr1, columnsArray);
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
                        if (item.equals("v.good"))
                            currentRow.setStyle("-fx-background-color:lightgreen");
                        if (item.equals("v.bad"))
                            currentRow.setStyle("-fx-background-color:red");
                    }
                }
            };
        });
    }

    public void handleShowData(TextField textView, String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        String[] result = null;
        while ((line = br.readLine()) != null)
            result = line.split(",");
        assert result != null;
        textView.setText(result[9]);
    }

    public void handleConfirm() {
        isChanged = true;
    }

    public void handleResult(String path, TextArea textArea) throws IOException {
        String line;
        StringBuilder content = new StringBuilder();
        FileReader fileReader = new FileReader(path);
        BufferedReader buffer = new BufferedReader(fileReader);

        while ((line = buffer.readLine()) != null) {
            content.append(line);
            content.append("\n");
        }
        buffer.close();
        textArea.setText(content.toString());
    }

    @FXML
    public void handleProcessing(int classifier) {
        String content = WekaManager.makeTest("TrainingData.arff", classifier);
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
}
