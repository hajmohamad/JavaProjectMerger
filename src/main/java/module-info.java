module com.example.projectmerger {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectmerger to javafx.fxml;
    exports com.example.projectmerger;
}