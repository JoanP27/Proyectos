package edu.joan.fantasyfx.model;

import java.io.Serializable;
import java.time.LocalDate;

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

    public Item(String code, String name, String type, String rarity, LocalDate date) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override public String toString()
    {
        return this.code + ";" +  this.name + ";" + this.type + ";" + this.rarity + ";" + this.date;
    }

}
