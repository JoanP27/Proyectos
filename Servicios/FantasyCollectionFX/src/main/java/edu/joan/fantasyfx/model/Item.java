package edu.joan.fantasyfx.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Item implements Serializable {
    private String code;
    private String name;
    private String type;
    private String rarity;
    private LocalDate date;
    public Item(String code)
    {
        this.code = code;
    }

    /**
     * creates an item with the passed parameters
     * @param code the code of the item
     * @param name the name of the item
     * @param type the type of the item
     * @param rarity the rarity of the item
     * @param date the obtained date of the item
     */
    public Item(String code, String name, String type, String rarity, LocalDate date) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.date = date;
    }

    /**
     * Gives the code of the item
     * @return a string representing the code of the item
     */
    public String getCode() {
        return code;
    }

    /**
     * sets the code of the item
     * @param code code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * gets the name of the item
     * @return String representing the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the item
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the type of the item
     * @return String representing the type of the item
     */
    public String getType() {
        return type;
    }

    /**
     * sets the type of the item
     * @param type type to be set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * gets the rarity of the item
     * @return String representing the rarity of the item
     */
    public String getRarity() {
        return rarity;
    }

    /**
     * Sets the rarity of the item
     * @param rarity rarity to set
     */
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    /**
     * Gets the obtained date in a formatted string
     * @return String representing the obtained date of the item
     */
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(date);
    }

    /**
     * Sets the date of the item
     * @param date the LocalDate to be set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override public String toString()
    {
        return this.code + ";" +  this.name + ";" + this.type + ";" + this.rarity + ";" + this.getDate();
    }

}
