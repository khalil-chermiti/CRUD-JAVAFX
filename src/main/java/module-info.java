module com.example.tp {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires org.hibernate.orm.core;


    opens com.example.tp;
    opens com.example.tp.models;
    opens com.example.tp.utils;
    opens com.example.tp.controllers;
    opens com.example.tp.DAO;

    exports com.example.tp;
    exports com.example.tp.models;
    exports com.example.tp.utils;
    exports com.example.tp.controllers;
    exports com.example.tp.DAO;
}