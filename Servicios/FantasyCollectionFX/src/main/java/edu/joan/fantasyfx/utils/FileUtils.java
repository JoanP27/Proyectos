package edu.joan.fantasyfx.utils;

import edu.joan.fantasyfx.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
    public static List<Item> loadItems() throws FileNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("items.txt")))
        {
            ArrayList<Item> items = (ArrayList<Item>)ois.readObject();
            return items;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
         /*List<Item> items = new ArrayList<Item>();
        try (Stream<String> reader = Files.lines(Paths.get("books.txt"))) {
            items.addAll(reader.map(s->new Item(s.split(";")[0],s.split(";")[1],s.split(";")[2],s.split(";")[3], LocalDate.parse(s.split(";")[4]))).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;*/
    }
    public static void saveItems(List<Item> items) throws FileNotFoundException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("items.txt")))
        {
            oos.writeObject(items);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
