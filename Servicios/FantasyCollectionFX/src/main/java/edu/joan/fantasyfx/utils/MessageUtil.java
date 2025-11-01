package edu.joan.fantasyfx.utils;

import javafx.scene.control.Alert;

import java.util.List;

public class MessageUtil {
    /**
     * Shows a alert message with the information passed by parameters
     * @param title the title of the message
     * @param message the content of the message
     */
    public static void showInfoMessage(String title, String message) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Shows a alert message with the information passed by parameters
     * @param title the title of the message
     * @param messages the list of to set as content of the message
     */
    public static void showInfoMessage(String title, List<String> messages)
    {
        Alert dialog =  new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setContentText(messages.toString());
        dialog.showAndWait();
    }

    /**
     * Shows an error message with the information passed by parameters
     * @param title title of the error
     * @param message content of the error
     */
    public static void showErrorMessage(String title, String message)
    {
        Alert dialog =  new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.showAndWait();
    }
}
