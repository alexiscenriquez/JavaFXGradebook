module com.gradebook.gradebook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires com.jfoenix;

    opens com.gradebook.gradebook to javafx.fxml;
    exports com.gradebook.gradebook;
    exports com.gradebook.gradebook.controllers;
    opens com.gradebook.gradebook.controllers to javafx.fxml;
}