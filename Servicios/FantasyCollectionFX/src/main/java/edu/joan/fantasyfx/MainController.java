package edu.joan.fantasyfx;

import edu.joan.fantasyfx.model.Item;
import edu.joan.fantasyfx.utils.FileUtils;
import edu.joan.fantasyfx.utils.MessageUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    @FXML private Button btAdd;
    @FXML private Button btDel;
    @FXML private Button btUpd;
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

        populateFields();

        try {
            loadItems();

            showAllItems();
            tvItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                txCode.setText(newValue.getCode());
                txName.setText(newValue.getName());
                cbType.setValue(newValue.getType());
                cbRarity.setValue(newValue.getRarity());
                dpObtained.setValue(LocalDate.parse(newValue.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            });

            cbFilter.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                switch (newValue.intValue()) {
                    case 0:
                        showAllItems();
                        break;
                    case 1:
                        showSameTypeAsSelected();
                        break;
                    case 2:
                        showOnlyLegendaryItems();
                        break;
                    case 3:
                        showMostRecent();
                        break;
                    case 4:
                        showPercentage();
                        break;
                }
            });

            txSearch.setOnKeyReleased(event -> {
                search(txSearch.getText());
            });

            btAdd.setOnAction(event -> {
                try{
                    addItem();
                }catch(Exception ex){
                    MessageUtil.showErrorMessage("ERROR", ex.getMessage());
                }
            });
            btDel.setOnAction(event -> {
                try{
                    removeItem();
                }catch(Exception ex){
                    MessageUtil.showErrorMessage("ERROR", ex.getMessage());
                }
            });
            btUpd.setOnAction(event -> {
                try{
                    updateItem();
                }catch(Exception ex){
                    MessageUtil.showErrorMessage("ERROR", ex.getMessage());
                }
            });
        }catch(Exception e){
            MessageUtil.showErrorMessage("ERROR", e.getMessage());
        }


    }

    public void setOnCloseListener(Stage stage) {

        stage.setOnCloseRequest(event -> {

            event.consume();

            try
            {
                saveItems();
            } catch (IOException e) {
                MessageUtil.showErrorMessage("ERROR", e.getMessage());
            }

            stage.close();
        });

    }

    /**
     * fills the fields of the formulary with some values
     */
    public void populateFields()
    {
        List<String> valoresFilter = new ArrayList<String>();
        valoresFilter.add("Show all items");
        valoresFilter.add("Show items of the same type as selected");
        valoresFilter.add("Show Legendary items");
        valoresFilter.add("Show last 5 obtained items");
        valoresFilter.add("Show average rarity distribution");
        cbFilter.setItems(FXCollections.observableArrayList(valoresFilter));
        cbFilter.getSelectionModel().select(0);

        List<String> valoresType = new ArrayList<String>();
        valoresType.clear();
        valoresType.add("Weapon");
        valoresType.add("Potion");
        valoresType.add("Armor");
        valoresType.add("Artifact");
        cbType.setItems(FXCollections.observableList(valoresType));
        cbType.getSelectionModel().select(0);

        List<String> valoresRarity = new ArrayList<String>();
        valoresRarity.clear();
        valoresRarity.add("Common");
        valoresRarity.add("Rare");
        valoresRarity.add("Epic");
        valoresRarity.add("Legendary");
        cbRarity.setItems(FXCollections.observableList(valoresRarity));
        cbRarity.getSelectionModel().select(0);
    }

    /**
     * shows all the items on the tableView
     */
    public void showAllItems()
    {
        tvItems.setItems(FXCollections.observableList(items));
    }

    /**
     * filters all items depending of the type of the selected ite on the tableView and shows it in the tableview,
     * throws an exception if no item is selected
     */
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
    /**
     * filters all items by the rarity set on the rarity field of the formulary
     */
    public void showOnlyLegendaryItems()
    {
        List<Item> filteredItems = items.stream().filter(i -> i.getRarity().equals("Legendary")).toList();
        tvItems.setItems(FXCollections.observableList(filteredItems));
    }

    /**
     * Sorts the items by obtained date in descendent mode, then gets the first 5 items of the list
     */
    public void showMostRecent()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Item> orderedItems = items.stream().sorted((i1,i2) ->
                LocalDate.parse(i2.getDate(), formatter)
                        .compareTo(LocalDate.parse(i1.getDate(), formatter))).limit(5)
                .toList();
        tvItems.setItems(FXCollections.observableList(orderedItems));

    }

    /**
     * Groups the percentage by rarity and counts the amount of items of each rarity, then gets the percentages of each count
     * and shows it in a message box
     */
    public void showPercentage()
    {
        List<String> percentages = new ArrayList<String>();
        items.stream().collect(Collectors.groupingBy(i -> i.getRarity(), Collectors.counting())).forEach(
                (k,v) -> percentages.add(k + ": " + (v/(double)items.size()) * 100d+"%")
        );
        MessageUtil.showInfoMessage("Porcentages", percentages);
    }

    /**
     * Search for items if the passed string is in any of its properties
     * @param value String to use to search
     */
    public void search(String value)
    {
        List<Item> filteredItems = items.stream().filter(i ->
                i.getName().toLowerCase().contains(value.toLowerCase()) ||
                i.getCode().toLowerCase().contains(value.toLowerCase()) ||
                i.getType().toLowerCase().contains(value.toLowerCase()) ||
                i.getRarity().toLowerCase().contains(value.toLowerCase()) ||
                i.getDate().toLowerCase().contains(value.toLowerCase())
        ).toList();
        tvItems.setItems(FXCollections.observableList(filteredItems));
    }

    /**
     * Adds an item using the fields of the formulary
     */
    public void addItem()
    {
        items.add(new Item(
                txCode.getText(),
                txName.getText(),
                cbType.getValue().toString(),
                cbRarity.getValue().toString(),
                dpObtained.getValue()
        ));
        tvItems.setItems(FXCollections.observableList(items));
        showAllItems();
    }

    /**
     * Removes the item when its code matches with the code field of the formulary
     */
    public void removeItem()
    {
        items.remove(items.stream().filter(i -> i.getCode().toLowerCase().equals(txCode.getText().toLowerCase())).findFirst().orElse(null));
        tvItems.setItems(FXCollections.observableList(items));
        showAllItems();
    }

    /**
     * Loads every item of the items txt file and throws a exception if failed
     * @throws FileNotFoundException thrown when the file is missing
     * @throws IOException thrown when it fails to load the file
     */
    public void loadItems() throws FileNotFoundException, IOException
    {
        items = FileUtils.loadItems();
    }

    /**
     * Saves every item of the items list into the items txt file and throws a exception if failed
     * @throws FileNotFoundException thrown when the file is missing
     * @throws IOException thrown when it fails to save the file
     */
    public void saveItems() throws FileNotFoundException, IOException
    {
        FileUtils.saveItems(items);
    }

    /**
     *
     * Updates the item properties using the formulary fields
     *
     * @throws NullPointerException thrown when there is no item selected
     */
    public void updateItem() throws NullPointerException
    {
        Item item = tvItems.getSelectionModel().getSelectedItem();

        if(item == null) {
            throw new NullPointerException("You must select an item");
        }

        item.setName(txName.getText());
        item.setCode(txCode.getText());
        item.setType(cbType.getValue().toString());
        item.setRarity(cbRarity.getValue().toString());
        item.setDate(dpObtained.getValue());

        System.out.println(items);

        tvItems.setItems(FXCollections.observableList(new  ArrayList<Item>()));
        tvItems.refresh();

        showAllItems();


    }
}
