module com.example.willhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.willhero to javafx.fxml;
    exports com.example.willhero;
}