package com.example.boardgamesshop.Controllers;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.lang.Math;
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
    private Button add_to_cart_button;

    @FXML
    private ListView<String> cart_list;

    @FXML
    private Button reviews_button;

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

    @FXML
    private TextField cart_total_price;

    @FXML
    private TextField cart_number_of_items;

    private String current_user_id;
    public void setLogin(String current_user_id) {
        this.current_user_id = current_user_id;
    }

    @FXML
    private ComboBox<String> order_changestatus_combobox; // Assuming the combo box contains statuses

    @FXML
    private Button order_cancel_button;

    @FXML
    private TableView<Order> order_table; // Assuming a class Order exists

    @FXML
    private TableColumn<Order, String> order_name; // Assuming Order has a getCustomerName() method

    @FXML
    private TableColumn<Order, String> order_date; // Assuming Order has a getDate() method

    @FXML
    private TableColumn<Order, String> order_status; // Assuming Order has a getStatus() method

    @FXML
    private TableColumn<Order, String> order_id;

    @FXML
    private Button filter_button;

    @FXML
    private Button cancel_filter_button;

    @FXML
    private DatePicker filter_date;

    @FXML
    private DatePicker filter_date_end;

    @FXML
    private ComboBox<String> filter_name; // Assuming the combo box contains customer names

    @FXML
    private ComboBox<String> filter_status; // Assuming the combo box contains statuses

    private ObservableList<Order> orders; // ObservableList to store Order objects



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

        orders = FXCollections.observableArrayList();
        order_table.setItems(orders);
        order_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        order_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        order_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        order_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            loadOrders();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

        ObservableList<String> statuses = FXCollections.observableArrayList("PENDING", "CONFIRMED", "COMPLETED");
        order_changestatus_combobox.setItems(statuses);
        filter_status.setItems(statuses);
        order_changestatus_combobox.setItems(statuses);
        ObservableList<String> order_names = FXCollections.observableArrayList();
        ObservableList<Order> orders = order_table.getItems();
        for (Order order : orders) {
            if(!order_names.contains(order.getCustomer_name())) {
                order_names.add(order.getCustomer_name());
            }
        }
        filter_name.setItems(order_names);

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

        add_to_cart_button.setOnAction(event -> {
            try{
                Prod prod = product_table.getSelectionModel().getSelectedItem();
                ObservableList<String> cart = FXCollections.observableArrayList();
                cart.addAll(cart_list.getItems());
                String prod_string = "Product ID: " + prod.getId()+"\nName: "+prod.getName()+"\nPrice: "+ prod.getPrice()+"$";
                cart.add(prod_string);
                cart_list.setItems(cart);
                cart_update(cart);
            }
            catch(Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, product was not selected");
                alert.setContentText("Select product and try again");
                alert.showAndWait();
            }
        });

        remove_from_cart_button.setOnAction(event -> {
            try{
                ObservableList<String> cart = FXCollections.observableArrayList();
                cart.addAll(cart_list.getItems());
                cart.remove(cart_list.getSelectionModel().getSelectedIndex());
                cart_list.setItems(cart);
                cart_update(cart);
            }
            catch(Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, product was not selected");
                alert.setContentText("Select product and try again");
                alert.showAndWait();
            }
        });

        buy_button.setOnAction(event -> {
            if(cart_list.getItems() != null && !cart_list.getItems().isEmpty())
            {
                List<String> order = cart_list.getItems();
                List<String> items = new ArrayList<>();
                for( String item : order)
                {
                    items.add( item.split("\n",3)[0].replace("Product ID: ","").trim());
                }

                try {
                    DBHandler dbHandler = new DBHandler();
                    dbHandler.AddOrder(current_user_id,items);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification");
                    alert.setHeaderText("Success");
                    alert.setContentText("Order was added successfully");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    loadProducts();
                    loadOrders();
                    ObservableList<String> cart = FXCollections.observableArrayList();
                    cart_list.setItems(cart);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setContentText("Error");
                alert.showAndWait();
            }
        });

        filter_button.setOnAction(event -> {
            LocalDate startDate = filter_date.getValue();
            LocalDate endDate = filter_date_end.getValue();

            // Check if both dates are selected
            if (startDate != null && endDate != null && !filter_name.getSelectionModel().getSelectedItem().isEmpty() && !filter_status.getSelectionModel().getSelectedItem().isEmpty()) {
                ObservableList<Order> temp = order_table.getItems();
                ObservableList<Order> filtered = FXCollections.observableArrayList();
                for (Order order : temp) {
                    LocalDate orderDate = order.getOrder_date().toLocalDate();
                    if (order.getCustomer_name().equals(filter_name.getSelectionModel().getSelectedItem())
                            && order.getStatus().equals(filter_status.getSelectionModel().getSelectedItem())
                            && !orderDate.isBefore(startDate) // Order date is after or equal to start date
                            && !orderDate.isAfter(endDate)) { // Order date is before or equal to end date
                        filtered.add(order);
                    }
                }
                order_table.setItems(filtered);
            }
        });

        cancel_filter_button.setOnAction(event -> {
            try {
                loadOrders();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        // Order status change button action
        order_changestatus_combobox.setOnAction(event -> {
            if (order_table.getSelectionModel().getSelectedItem() != null) {
                String newStatus = order_changestatus_combobox.getSelectionModel().getSelectedItem();
                Order selectedOrder = order_table.getSelectionModel().getSelectedItem();

                DBHandler dbHandler = new DBHandler();
                dbHandler.updateOrderStatus(selectedOrder.getId(), newStatus);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Order status was updated successfully");
                try {
                    loadOrders();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                //order_changestatus_combobox = null;
                //order_changestatus_combobox.setItems(statuses);
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Order status was not updated");
                alert.setContentText("Check information and try again");
                alert.showAndWait();
            }
        });

        order_cancel_button.setOnAction(event -> {
            if (order_table.getSelectionModel().getSelectedItem() != null) {
                Order selectedOrder = order_table.getSelectionModel().getSelectedItem();
                if(selectedOrder.getStatus().equals("PENDING"))
                {
                DBHandler dbHandler = new DBHandler();
                dbHandler.deleteOrder(selectedOrder.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Order was canceled successfully");
                try {
                    loadOrders();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                alert.showAndWait();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Order status in not pending");
                    alert.setContentText("You can cancel only pending orders");
                    alert.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, order was not selected");
                alert.setContentText("Select order and try again");
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
        else if (event.getSource() == order_table) {
            Order order = order_table.getSelectionModel().getSelectedItem();

            filter_status.getSelectionModel().select(order.getStatus());
            filter_date.setValue(order.getOrder_date().toLocalDate().minusYears(1));
            filter_date_end.setValue(order.getOrder_date().toLocalDate());
            filter_name.getSelectionModel().select(order.getCustomer_name());
        }
    }

    void cart_update(ObservableList<String> cart)    {
        double price = 0;
        String items_num = String.valueOf(cart.size());

        List<String> list = cart_list.getItems();
        for( String item : list)
        {
            String temp_price = item.split("\n",3)[2];
            temp_price = temp_price.replace("Price:","").replace("$","").trim();
            price += Double.parseDouble(temp_price);
        }
        cart_number_of_items.setText(String.valueOf(items_num));
        cart_total_price.setText(String.valueOf(Math.round(price*100)/100.0));
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

    public class Order{
        private int id;
        private String customerID;
        private String customer_name;
        private Date order_date;
        private String status;

        public Order(int id, String customerID, String customer_name, Date description, String status) {
            this.id = id;
            this.customerID = customerID;
            this.order_date = description;
            this.status = status;

            DBHandler dbHandler = new DBHandler();
            String query = "SELECT name, surname FROM boardgamesshopdb.users where id = ?;";
            try {
                Connection con = dbHandler.getDbConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, customerID);
                ResultSet usersList = pstmt.executeQuery();
                usersList.next();
                this.customer_name = usersList.getString("name") + " " + usersList.getString("surname");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCustomerID() {
            return customerID;
        }

        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public Date getOrder_date() {
            return order_date;
        }

        public void setOrder_date(Date description) {
            this.order_date = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    private void loadOrders() throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        Connection con = dbHandler.getDbConnection();
        ObservableList<Order> orders = FXCollections.observableArrayList();

        try (Statement stmt = con.createStatement()) {
            // Execute the query assuming your table's column names are accurate
            ResultSet orderList = stmt.executeQuery("SELECT * FROM orders");

            // Create an empty ObservableList to store Prod objects

            // Extract data and create Prod objects
            while (orderList.next()) {
                int id = orderList.getInt("id");
                String customerID = orderList.getString("customer_id");
                Date order_date = orderList.getDate("order_date");
                String status = orderList.getString("status");
                orders.add(new Order(id, customerID,"", order_date, status));
            }

            // Set the items of the TableView with the populated product list
            order_table.setItems(orders);
            order_table.setItems(orders);
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