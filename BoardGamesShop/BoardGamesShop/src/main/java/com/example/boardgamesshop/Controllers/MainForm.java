package com.example.boardgamesshop.Controllers;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.boardgamesshop.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.boardgamesshop.DB.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<User_struct, Date> user_birthday;

    @FXML
    private TableColumn<User_struct, String> user_contact_info;

    @FXML
    private TableColumn<User_struct, Integer> user_id;

    @FXML
    private TableColumn<User_struct, String> user_name;

    @FXML
    private TableColumn<User_struct, String> user_surname;

    @FXML
    private TableColumn<User_struct, String> user_password;

    @FXML
    private TableView<User_struct> user_table;

    @FXML
    private Button user_update_button;

    @FXML
    private Button user_delete_button;

    @FXML
    private DatePicker input_user_birthday;

    @FXML
    private TextField input_user_contact;

    @FXML
    private TextField input_user_id;

    @FXML
    private TextField input_user_name;

    @FXML
    private TextField input_user_surname;

    @FXML
    private TextField input_user_password;


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

        user_id.setCellValueFactory(new PropertyValueFactory<User_struct, Integer>("id"));
        user_name.setCellValueFactory(new PropertyValueFactory<User_struct, String>("name"));
        user_surname.setCellValueFactory(new PropertyValueFactory<User_struct,String>("surname"));
        user_contact_info.setCellValueFactory(new PropertyValueFactory<User_struct,String>("contact_info"));
        user_birthday.setCellValueFactory(new PropertyValueFactory<User_struct,Date>("date_of_birth"));
        user_password.setCellValueFactory(new PropertyValueFactory<User_struct,String>("password"));

        try {
            loadProducts();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            loadUsers();
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
            if(!input_id.getText().equals(""))
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

        user_update_button.setOnAction(event -> {
            if(!input_user_name.getText().equals("") && !input_user_surname.getText().equals("") && !input_user_contact.getText().equals("") && !input_user_password.getText().equals("")  )
            {
                int id = Integer.parseInt(input_user_id.getText());
                String name = input_user_name.getText();
                String surname = input_user_surname.getText();
                String contact = input_user_contact.getText();
                Date birthday = Date.valueOf(input_user_birthday.getValue());
                String password = input_user_password.getText();

                DBHandler dbHandler = new DBHandler();
                dbHandler.UpdateUser(id,name, surname, contact, birthday, password);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("User was updated successfully");
                try {
                    loadUsers();
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

        user_delete_button.setOnAction(event -> {
            if(!input_user_id.getText().equals(""))
            {
                int id = Integer.parseInt(input_user_id.getText());

                DBHandler dbHandler = new DBHandler();
                dbHandler.DeleteUser(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("User was deleted successfully");
                try {
                    loadUsers();
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
        else if (event.getSource() == user_table) {
            User_struct user = user_table.getSelectionModel().getSelectedItem();

            input_user_id.setText(String.valueOf(user.getId()));
            input_user_name.setText(String.valueOf(user.getName()));
            input_user_surname.setText(String.valueOf(user.getSurname()));
            input_user_contact.setText(String.valueOf(user.getContact_info()));
            input_user_birthday.setValue(user.getDate_of_birth().toLocalDate());
            input_user_password.setText(String.valueOf(user.getPassword()));
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

    public class User_struct{
        private int id;
        private String name;
        private String surname;
        private String contact_info;
        private Date date_of_birth;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getContact_info() {
            return contact_info;
        }

        public void setContact_info(String contact_info) {
            this.contact_info = contact_info;
        }

        public Date getDate_of_birth() {
            return date_of_birth;
        }

        public void setDate_of_birth(Date birthday) {
            this.date_of_birth = birthday;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public User_struct(int id, String name, String surname, String contact_info, Date date_of_birth, String password) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.contact_info = contact_info;
            this.date_of_birth = date_of_birth;
            this.password = password;
        }

        private String password;
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

    private void loadUsers() throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        Connection con = dbHandler.getDbConnection();
        ObservableList<User_struct> users = FXCollections.observableArrayList();

        try (Statement stmt = con.createStatement()) {
            // Execute the query assuming your table's column names are accurate
            ResultSet usersList = stmt.executeQuery("SELECT * FROM users");

            // Create an empty ObservableList to store Prod objects

            // Extract data and create Prod objects
            while (usersList.next()) {
                int id = usersList.getInt("id");
                String name = usersList.getString("name");
                String surname = usersList.getString("surname");
                String contact_info = usersList.getString("contact_info");
                Date date_of_birth = usersList.getDate("date_of_birth");
                String password = usersList.getString("password_hash");

                users.add(new User_struct(id, name, surname, contact_info, date_of_birth, password));
            }

            // Set the items of the TableView with the populated product list
            user_table.setItems(users);
        } catch (SQLException e) {
            // Handle the SQLException appropriately
            System.err.println("Error loading products: " + e.getMessage());
        } finally {
            // Close the connection
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " +  e.getMessage());
            }
        }
    }





}