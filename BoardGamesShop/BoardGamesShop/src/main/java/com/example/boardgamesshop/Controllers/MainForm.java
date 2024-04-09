package com.example.boardgamesshop.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.boardgamesshop.Model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.example.boardgamesshop.DB.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainForm implements Initializable {

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
    private TableView<Prod> product_table;

    @FXML
    private TableColumn<Prod, Integer> id;

    @FXML
    private TableColumn<Prod, String> name;

    @FXML
    private TableColumn<Prod, String> description;

    @FXML
    private TableColumn<Prod, Integer> quantity;

    @FXML
    private TableColumn<Prod, Double> price;
    @FXML
    private TableColumn<Prod, Integer> prod_id;

    @FXML
    private TableColumn<Prod, String> prod_name;

    @FXML
    private TableColumn<Prod, String> prod_description;

    @FXML
    private TableColumn<Prod, Integer> prod_quantity;

    @FXML
    private TableColumn<Prod, Double> prod_price;

    @FXML
    private TableView<Prod> prod_table;
    @FXML
    private Button remove_from_cart_button;

    @FXML
    private TextField input_id;

    @FXML
    private TextField input_description;

    @FXML
    private TextField input_name;

    @FXML
    private TextField input_price;

    @FXML
    private TextField input_quantity;
    @FXML
    private Button product_update_button;
    @FXML
    private Button product_delete_button;
    @FXML
    private Button product_add_button;

    @FXML
    private TableColumn<?, ?> user_birthday;

    @FXML
    private TableColumn<?, ?> user_contact_info;

    @FXML
    private Button user_delete_button;

    @FXML
    private TableColumn<?, ?> user_id;

    @FXML
    private TableColumn<?, ?> user_name;

    @FXML
    private TableColumn<?, ?> user_surname;

    @FXML
    private TableView<?> user_table;

    @FXML
    private Button user_update_button;


    @FXML @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Prod, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Prod,String>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<Prod,Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Prod,Double>("price"));

        prod_id.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("id"));
        prod_name.setCellValueFactory(new PropertyValueFactory<Prod, String>("name"));
        prod_description.setCellValueFactory(new PropertyValueFactory<Prod,String>("description"));
        prod_quantity.setCellValueFactory(new PropertyValueFactory<Prod,Integer>("quantity"));
        prod_price.setCellValueFactory(new PropertyValueFactory<Prod,Double>("price"));

        try {
            loadProducts();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        product_update_button.setOnAction(event -> {
            if(!input_name.getText().equals("") && !input_description.getText().equals("") && !input_quantity.getText().equals("") && !input_price.getText().equals(""))
            {
                int id = Integer.parseInt(input_id.getText());
                String name = input_name.getText();
                String description = input_description.getText();
                int quantity = Integer.parseInt(input_quantity.getText());
                double price = Double.parseDouble(input_price.getText());

                DBHandler dbHandler = new DBHandler();
                dbHandler.UpdateProduct(id,name,description, price, quantity);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Product was updated successfully");
                try {
                    loadProducts();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, some data is empty");
                alert.setContentText("Enter information and try again");
                alert.showAndWait();
            }
        });
        product_delete_button.setOnAction(event -> {
            if(!input_name.getText().equals("") && !input_description.getText().equals("") && !input_quantity.getText().equals("") && !input_price.getText().equals(""))
            {
                int id = Integer.parseInt(input_id.getText());

                DBHandler dbHandler = new DBHandler();
                dbHandler.DeleteProduct(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Product was deleted successfully");
                try {
                    loadProducts();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, product was not deleted successfully");
                alert.setContentText("Check information and try again");
                alert.showAndWait();
            }
        });
        product_add_button.setOnAction(event -> {
            if(!input_name.getText().equals("") && !input_description.getText().equals("") && !input_quantity.getText().equals("") && !input_price.getText().equals(""))
            {
                String name = input_name.getText();
                String description = input_description.getText();
                int quantity = Integer.parseInt(input_quantity.getText());
                double price = Double.parseDouble(input_price.getText());

                DBHandler dbHandler = new DBHandler();
                dbHandler.AddProduct(name,description, price, quantity);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Product was added successfully");
                try {
                    loadProducts();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, some data is empty");
                alert.setContentText("Enter information and try again");
                alert.showAndWait();
            }
        });
    }
    @FXML
    void rowClicked(MouseEvent event) {
        Prod prod;
        // Check which TableView was clicked and retrieve the selected item
        if (event.getSource() == prod_table) {
            prod = prod_table.getSelectionModel().getSelectedItem();

            input_id.setText(String.valueOf(prod.getId()));
            input_name.setText(String.valueOf(prod.getName()));
            input_description.setText(String.valueOf(prod.getDescription()));
            input_quantity.setText(String.valueOf(prod.getQuantity()));
            input_price.setText(String.valueOf(prod.getPrice()));
        }
        else if (event.getSource() == product_table) {
            prod = product_table.getSelectionModel().getSelectedItem();
        }
    }


    public class Prod { // Assuming a separate class for Prod structure
        private int id;
        private String name;
        private String description;
        private int quantity;
        private double price;

        public Prod(int id, String name, String description, int quantity, double price ) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    private void loadProducts() throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        Connection con = dbHandler.getDbConnection();
        ObservableList<Prod> products = FXCollections.observableArrayList();

        try (Statement stmt = con.createStatement()) {
            // Execute the query assuming your table's column names are accurate
            ResultSet productList = stmt.executeQuery("SELECT * FROM products");

            // Create an empty ObservableList to store Prod objects

            // Extract data and create Prod objects
            while (productList.next()) {
                int id = productList.getInt("id");
                String name = productList.getString("name");
                String description = productList.getString("description");
                int quantity = productList.getInt("quantity");
                double price = productList.getDouble("price");

                products.add(new Prod(id, name, description, quantity, price));
            }

            // Set the items of the TableView with the populated product list
            product_table.setItems(products);
            prod_table.setItems(products);
        } catch (SQLException e) {
            // Handle the SQLException appropriately (e.g., display error message)
            System.err.println("Error loading products: " + e.getMessage());
        } finally {
            // Close the connection (assuming it's managed by DBHandler)
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " +  e.getMessage());
            }
        }
    }





}