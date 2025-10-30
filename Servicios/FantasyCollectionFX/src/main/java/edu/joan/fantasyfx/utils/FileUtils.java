package edu.joan.fantasyfx.utils;

import edu.joan.fantasyfx.model.Item;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils
{
    public static List<Item> loadItems() throws FileNotFoundException {
        /*try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("items.txt")))
        {
            ArrayList<Item> items = (ArrayList<Item>)ois.readObject();
            return items;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
         List<Item> items = new ArrayList<Item>();
        try (Stream<String> reader = Files.lines(Paths.get("items.txt"))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            items.addAll(reader.map(s->new Item(s.split(";")[0],s.split(";")[1],s.split(";")[2],s.split(";")[3], LocalDate.parse(s.split(";")[4], formatter))).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
    public static void saveItems(List<Item> items) throws FileNotFoundException {
       /* try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("items.txt")))
        {
            oos.writeObject(items);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        try (PrintWriter printWriter = new PrintWriter("items.txt")) {
            for (Item item : items) {
                printWriter.println(item.toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
