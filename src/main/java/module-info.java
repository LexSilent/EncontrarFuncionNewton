module com.example.encontrarfuncionnewton {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;

    opens com.example.encontrarfuncionnewton to javafx.fxml;
    exports com.example.encontrarfuncionnewton;
}