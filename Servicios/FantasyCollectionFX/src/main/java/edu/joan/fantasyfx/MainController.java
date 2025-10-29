package edu.joan.fantasyfx;

import edu.joan.fantasyfx.model.Item;
import edu.joan.fantasyfx.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements javafx.fxml.Initializable {
    @FXML private TableColumn<Item, String> colCode;
    @FXML private TableColumn<Item, String> colName;
    @FXML private TableColumn<Item, String> colType;
    @FXML private TableColumn<Item, String> colRarity;
    @FXML private TableColumn<Item, LocalDate> colObtained;
    @FXML private Label lbCode;
    @FXML private TextField txCode;
    @FXML private Label lbName;
    @FXML private TextField txName;
    @FXML private Label lbType;
    @FXML private ChoiceBox cbType;
    @FXML private Label lbRarity;
    @FXML private ChoiceBox cbRarity;
    @FXML private Label lbObtained;
    @FXML private DatePicker dpObtained;
    @FXML private Button btAddObtained;
    @FXML private Button btDelObtained;
    @FXML private Button btUpdObtained;
    @FXML private Label lbFilter;
    @FXML private ChoiceBox cbFilter;
    @FXML private Label lbSearch;
    @FXML private TextField txSearch;
    @FXML private TableView<Item> tvItems;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRarity.setCellValueFactory(new PropertyValueFactory<>("rarity"));
        colObtained.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            loadItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<Item> items = new ArrayList<>();
        items.add(new Item("A1", "A", "A", "A", LocalDate.now()));
        try {
            FileUtils.saveItems(items);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadItems()
    {
        try {
            List<Item> items = FileUtils.loadItems();
            tvItems.setItems(FXCollections.observableList(items));
            items.forEach(i -> System.out.println(i));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
