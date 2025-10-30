package edu.joan.fantasyfx;

import edu.joan.fantasyfx.model.Item;
import edu.joan.fantasyfx.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private List<Item> items;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRarity.setCellValueFactory(new PropertyValueFactory<>("rarity"));
        colObtained.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadItems();
        showAllItems();
        tvItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txCode.setText(newValue.getCode());
            txName.setText(newValue.getName());
            cbType.setValue(newValue.getType());
            cbRarity.setValue(newValue.getRarity());
            dpObtained.setValue(LocalDate.parse(newValue.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        List<String> valores = new ArrayList<String>();
        valores.add("Show all items");
        valores.add("Show items of the same type as selected");
        valores.add("Show Legendary items");
        valores.add("Show last 5 obtained items");
        valores.add("Show average rarity distribution");
        cbFilter.setItems(FXCollections.observableArrayList(valores));

        cbFilter.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            switch(newValue.intValue())
            {
                case 0: showAllItems();  break;
                case 1: showSameTypeAsSelected(); break;
                case 2: showOnlyLegendaryItems(); break;
                case 3: showMostRecent(); break;
                case 4: showPercentage(); break;
            }
        });

    }
    public void showAllItems()
    {
        tvItems.setItems(FXCollections.observableList(items));
    }
    public void showSameTypeAsSelected()
    {
        List<Item> selectedItems = tvItems.getSelectionModel().getSelectedItems();

        if(selectedItems.size() == 0 || selectedItems.size() > 1)
        {
            throw new IllegalArgumentException("Please select only one item");
        }

        List<Item> filteredItems = items.stream().filter(i -> i.getType().equals(selectedItems.get(0).getType())).toList();
        tvItems.setItems(FXCollections.observableList(filteredItems));
    }
    public void showOnlyLegendaryItems()
    {
        List<Item> filteredItems = items.stream().filter(i -> i.getRarity().equals("Legendary")).toList();
        tvItems.setItems(FXCollections.observableList(filteredItems));
    }
    public void showMostRecent()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Item> orderedItems = items.stream().sorted((i1,i2) ->
                LocalDate.parse(i2.getDate(), formatter)
                        .compareTo(LocalDate.parse(i1.getDate(), formatter))).limit(5)
                .toList();
        tvItems.setItems(FXCollections.observableList(orderedItems));

    }
    public void showPercentage()
    {

    }
    public void loadItems()
    {
        try {
            items = FileUtils.loadItems();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
