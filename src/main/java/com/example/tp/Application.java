package com.example.tp;

import com.example.tp.models.Admin;
import com.example.tp.utils.ConnectionUtil;
import jakarta.persistence.Query;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @FXML
    private Pane anchor;

    @FXML
    public PasswordField password;

    @FXML
    public TextField username;

    @FXML

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Product Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeSceneToProduct() throws IOException {
        FXMLLoader LoginLoader = new FXMLLoader(getClass().getResource("product.fxml"));
        Parent parent = LoginLoader.load();
        anchor.getScene().setRoot(parent);
    }

    public void login() {
        if (password.getText().equalsIgnoreCase("") || username.getText().equalsIgnoreCase("")) {
            showAlert("empty field", "please fill all fields");
            return;
        }

        try (Session session = ConnectionUtil.getSessionAnnotationFactory().getCurrentSession()) {
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery("from Admin where username = :username");

            query.setParameter("username", username.getText());
            Admin admin = (Admin) query.getSingleResult();

            if (admin == null) {
                showAlert("wrong input", "no such user in database");
                return;
            }

            if (!admin.getPassword().equals(password.getText())) {
                showAlert("wrong input", "wrong password");
                return;
            }

            changeSceneToProduct();

        } catch (HibernateException | IOException exception) {
            if (exception instanceof HibernateException) {
                System.out.println("hibernate exception");
                ((HibernateException) exception).printStackTrace();
            }

            if (exception instanceof IOException) {
                System.out.println("can't change scene");
                ((IOException) exception).printStackTrace();
            }
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}