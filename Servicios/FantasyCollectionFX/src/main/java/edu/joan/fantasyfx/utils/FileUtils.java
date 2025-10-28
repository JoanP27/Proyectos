package edu.joan.fantasyfx.utils;

import edu.joan.fantasyfx.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
    static List<Item> loadItems() throws FileNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("items.txt")))
        {
            ArrayList<Item> items = (ArrayList<Item>)ois.readObject();
            return items;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static void saveItems(List<Item> items) throws FileNotFoundException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("items.txt")))
        {
            oos.writeObject(items);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
