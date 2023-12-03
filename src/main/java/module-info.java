module team.ocean {
    requires javafx.controls;
    requires javafx.fxml;


    opens team.ocean to javafx.fxml;
    exports team.ocean;
}