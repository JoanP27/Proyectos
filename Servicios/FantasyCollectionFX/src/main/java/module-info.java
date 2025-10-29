module edu.joan.fantasyfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.joan.fantasyfx to javafx.fxml;
    opens edu.joan.fantasyfx.model to javafx.base;
    exports edu.joan.fantasyfx;
    exports edu.joan.fantasyfx.model;
}