module com.mycompany.ForgottenPages {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.ForgottenPages.main to javafx.fxml;
    opens com.mycompany.ForgottenPages.controller to javafx.fxml;
    opens com.mycompany.ForgottenPages.model to javafx.fxml;

    exports com.mycompany.ForgottenPages.main;
    exports com.mycompany.ForgottenPages.controller;
    exports com.mycompany.ForgottenPages.model;
    exports com.mycompany.ForgottenPages.util;
    exports com.mycompany.ForgottenPages.dao;
}
