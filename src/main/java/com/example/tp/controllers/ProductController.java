package com.example.tp.controllers;

import com.example.tp.DAO.ProductDAO;
import com.example.tp.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private AnchorPane anchor;

    @FXML
    public TableView<Product> products;

    @FXML
    public TableColumn<Product, Integer> idColumn;
    @FXML
    public TableColumn<Product, String> titleColumn;
    public TableColumn<Product, Double> priceColumn;
    @FXML
    public TableColumn<Product, Integer> quantityColumn;
    ObservableList<Product> productList = FXCollections.observableArrayList();
    @FXML
    public TextField title;
    @FXML
    public TextField price;
    @FXML
    public TextField quantity;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set Column getters
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // fetch list of products
        ProductDAO productDAO = new ProductDAO();

        productList.addAll(productDAO.getProducts());

        products.setItems(productList);

    }


    public void addProduct() {
        if (!validateInputs()) {
            alert(false, "wrong foramt", "missing or wrong field format");
            return;
        }

        Product newProduct = new Product();
        newProduct.setTitle(this.title.getText());
        newProduct.setPrice(Double.parseDouble(this.price.getText()));
        newProduct.setQuantity(Integer.parseInt(this.quantity.getText()));

        if (new ProductDAO().addProduct(newProduct)) {
            productList.add(newProduct);
            alert(true, "success", "product added successfully");
            return;
        }

        alert(false, "error", "could not add product");
    }

    public void deleteProduct() {
        Product productToDelete = products.getSelectionModel().getSelectedItem();

        if (productToDelete == null) {
            alert(false, "error", "no product to delete");
            return;
        }

        if (new ProductDAO().removeProduct(productToDelete)) {
            alert(true, "success", "product deleted successfully");
            productList.remove(productToDelete);
            return;
        }

    }

    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        System.out.println(getClass().getResource("login.fxml"));
        Parent parent = loader.load();
        title.getScene().setRoot(parent);
    }

    private boolean validateInputs() {
        if (this.title.getText().equalsIgnoreCase("") || this.price.getText().equalsIgnoreCase("") || this.quantity.getText().equalsIgnoreCase("")) {
            alert(false, "empty filed", "please fill all fields");
            return false;
        }

        try {
            Double.parseDouble(this.price.getText());
            Integer.parseInt(this.quantity.getText());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
            return false;
        }

        return true;
    }

    private void alert(Boolean success, String title, String message) {
        Alert alert = null;

        if (success) alert = new Alert(Alert.AlertType.INFORMATION);

        if (!success) alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
