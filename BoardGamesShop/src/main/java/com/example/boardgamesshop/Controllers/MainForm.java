package com.example.boardgamesshop.Controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.boardgamesshop.DB.DBHandler;
import com.example.boardgamesshop.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buy_button;

    @FXML
    private ListView<?> cart_list;

    @FXML
    private Button leave_review_button;

    @FXML
    private ListView<Product> product_list;

    @FXML
    private Button remove_from_cart_button;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProducts();
    }

    private void loadProducts() {
        DBHandler dbHandler = new DBHandler();
        ResultSet productList = dbHandler.getProducts();

        ObservableList<Product> products = FXCollections.observableArrayList();


        int id =0;
        String name ="";
        String description ="";
        double price=0.0;
        String image_path="";
        int quantity=0;
        try {
            while (!productList.next()) {
                if (!productList.next())
                    break;
                id = productList.getInt("id");
                name = productList.getString("name");
                description = productList.getString("description");
                price = Double.parseDouble(productList.getString("price"));
                image_path = productList.getString("image_path");
                quantity = Integer.parseInt(productList.getString("quantity"));
            }

            Product product = new Product(id, name,description,price, image_path, quantity );
            products.add(product);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Set the items of the ListView
        product_list.setItems(products);
    }

}

