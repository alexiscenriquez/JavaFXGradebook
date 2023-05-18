module com.gradebook.gradebook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires com.jfoenix;

    opens gradebook to javafx.fxml;
    exports gradebook;
    exports gradebook.controllers;
    opens gradebook.controllers to javafx.fxml;
    opens gradebook.models to javafx.base;
}