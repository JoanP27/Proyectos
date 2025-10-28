module edu.joan.fantasyfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.joan.fantasyfx to javafx.fxml;
    exports edu.joan.fantasyfx;
}