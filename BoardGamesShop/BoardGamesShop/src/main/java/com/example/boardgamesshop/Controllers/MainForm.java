package com.example.boardgamesshop.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.example.boardgamesshop.DB.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Button remove_from_cart_button;

    @FXML @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Prod, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Prod, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Prod,String>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<Prod,Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Prod,Double>("price"));

        try {
            loadProducts();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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