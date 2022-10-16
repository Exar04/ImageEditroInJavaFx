module com.example.imageeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.imageeditor to javafx.fxml;
    exports com.example.imageeditor;
}