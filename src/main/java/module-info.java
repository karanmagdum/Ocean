module team.ocean {
    requires javafx.controls;
    requires javafx.fxml;
    //requires org.testng;


    opens team.ocean to javafx.fxml;
    exports team.ocean;
}