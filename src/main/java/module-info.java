module team.ocean {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;



    opens team.ocean to javafx.fxml;
    exports team.ocean;
}