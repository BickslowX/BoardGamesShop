package com.example.boardgamesshop.DB;

import com.example.boardgamesshop.Controllers.MainForm;
import com.example.boardgamesshop.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends Configs {

    Connection dbConnection;

    public ObservableList<String> getAdministratorsData() throws SQLException, ClassNotFoundException {
        Connection con = getDbConnection();
        ObservableList<String> admins = FXCollections.observableArrayList();

        try (Statement stmt = con.createStatement()) {
            // Execute the query assuming your table's column names are accurate
            ResultSet usersList = stmt.executeQuery("SELECT * FROM administrators");

            // Create an empty ObservableList to store Prod objects

            // Extract data and create Prod objects
            while (usersList.next()) {
                String id = usersList.getString("user_id");
                admins.add(id);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }

    public ObservableList<String> getEmployeesData() throws SQLException, ClassNotFoundException {
        Connection con = getDbConnection();
        ObservableList<String> emp = FXCollections.observableArrayList();

        try (Statement stmt = con.createStatement()) {
            // Execute the query assuming your table's column names are accurate
            ResultSet usersList = stmt.executeQuery("SELECT * FROM employees");

            // Create an empty ObservableList to store Prod objects

            // Extract data and create Prod objects
            while (usersList.next()) {
                String id = usersList.getString("user_id");
                emp.add(id);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emp;
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Connection dbConnection;

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }
    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getUser(User user)
    {
        ResultSet resSet = null;
        String select = "SELECT * FROM "+ Const.USERS_TABLE +
                " WHERE "+Const.USERS_NAME+"=?AND "+Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public void AddUser(String regLogin, String regPassword, String regSurname, String regContactInfo, String regBirthDate) {
        String user_table = "users";
        String user_name = "name";
        String user_surname = "surname";
        String user_info = "contact_info";
        String user_Birthday = "date_of_birth";
        String user_password = "password_hash";
        String insert = "INSERT INTO " + user_table
                + "(" + user_name + "," + user_surname + "," + user_info + "," + user_Birthday + ","+user_password+ ")"
                + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, regLogin);
            prSt.setString(2, regSurname);
            prSt.setString(3, regContactInfo);
            prSt.setString(4, String.valueOf(regBirthDate));
            prSt.setString(5, regPassword);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void UpdateProduct(int id, String name, String description, double price, int quantity) {
        String product_table = "products";
        String product_id = "id";
        String product_name = "name";
        String product_description = "description";
        String product_price = "price";
        String product_quantity = "quantity";
        //UPDATE `boardgamesshopdb`.`products`
        // SET `name` = 'Wingspann', `description` = 'Engine-building board game with a bird themee', `price` = '59.98', `quantity` = '41' WHERE (`id` = '5');
        String update = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, name);
            prSt.setString(2, description);
            prSt.setDouble(3, price); // Assuming price is a double
            prSt.setInt(4, quantity);
            prSt.setInt(5, id); // Update the product with ID = 5

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void DeleteProduct(int id)
    {
        String deleteQuery = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(deleteQuery)) {
            prSt.setInt(1, id);
            prSt.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void AddProduct(String name, String description, double price, int quantity) {
        String insert = "INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, name);
            prSt.setString(2, description);
            prSt.setDouble(3, price);
            prSt.setInt(4, quantity);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    public void UpdateUser(int id, String name, String surname, String contact, Date birthday, String password) {
        String update = "UPDATE users SET name = ?, surname = ?, contact_info = ?, date_of_birth = ?, password_hash = ? WHERE id = ?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(update)) {
            prSt.setString(1, name);
            prSt.setString(2, surname);
            prSt.setString(3, contact);
            prSt.setDate(4, birthday);
            prSt.setString(5, password);
            prSt.setInt(6, id);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }


    public void DeleteUser(int id) {
        String delete = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public void AddOrder(String currentUserId, List<String> items) throws SQLException, ClassNotFoundException {
        Connection con = getDbConnection();

        try {
            // Start a transaction to ensure data integrity
            con.setAutoCommit(false);

            // 1. Check product availability for each item in the order
            Map<String, Integer> productAvailability = checkProductAvailability(items);
            for (String productId : items) {
                // Get the requested quantity for the current product ID
                int requestedQuantity = getRequestedQuantity(productId, items); // New method to get requested quantity
                if (productAvailability.get(productId) == null || productAvailability.get(productId) < requestedQuantity) {
                    throw new InsufficientStockException("Insufficient stock for product: " + productId);
                }
            }

            // 2. Insert the order record into the 'orders' table
            String insertOrder = "INSERT INTO orders (customer_id, order_date, status) VALUES (?, NOW(), 'PENDING')";
            PreparedStatement orderPrst = con.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            orderPrst.setString(1, currentUserId);
            orderPrst.executeUpdate();

            // 3. Get the auto-generated order ID
            ResultSet rs = orderPrst.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // 4. Insert each item from the 'items' list into the 'order_items' table
            String insertItem = "INSERT INTO order_items (order_id, product_id) VALUES (?, ?)";
            PreparedStatement itemPrst = con.prepareStatement(insertItem);
            for (String productId : items) {
                itemPrst.setInt(1, orderId);
                itemPrst.setString(2, productId);
                itemPrst.addBatch();
            }
            itemPrst.executeBatch();

            // 5. Update product quantities after successful order placement
            String updateQuantity = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
            PreparedStatement updatePrst = con.prepareStatement(updateQuantity);
            for (String productId : items) {
                int requestedQuantity = getRequestedQuantity(productId, items);
                updatePrst.setInt(1, requestedQuantity);
                updatePrst.setString(2, productId);
                updatePrst.addBatch();
            }
            updatePrst.executeBatch();

            // Commit the transaction if all inserts are successful
            con.commit();

        } catch (SQLException | InsufficientStockException e) {
            // Handle exceptions appropriately (rollback transaction, provide error messages)
            con.rollback();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Error");
            alert.setContentText("Error adding order: " + e.getMessage());
            alert.showAndWait();
        } finally {
            // Close the connection
            try {
                con.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error");
                alert.setContentText("Error closing connection: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    // Helper method to check product availability (implementation details based on your schema)
    private Map<String, Integer> checkProductAvailability(List<String> productIds) throws SQLException, ClassNotFoundException {
        String checkAvailability = "SELECT id, quantity FROM products WHERE id IN (?)";
        PreparedStatement checkPrst = getDbConnection().prepareStatement(checkAvailability);

        // Create a comma-separated list of placeholders for product IDs
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < productIds.size(); i++) {
            placeholders.append("?");
            if (i != productIds.size() - 1) {
                placeholders.append(",");
            }
        }

        // Set placeholders for product IDs
        checkPrst.setString(1, placeholders.toString());
        for (int i = 0; i < productIds.size(); i++) {
            checkPrst.setString(i+1 , productIds.get(i));
        }

        Map<String, Integer> availabilityMap = new HashMap<>();
        ResultSet rs = checkPrst.executeQuery();
        while (rs.next()) {
            availabilityMap.put(rs.getString("id"), rs.getInt("quantity"));
        }

        return availabilityMap;
    }

    // Method to get requested quantity for a specific product ID from the order list
    private int getRequestedQuantity(String productId, List<String> items) {
        int quantity = 0;
        for (String item : items) {
            // Assuming items in the list represent product IDs (adapt based on your data structure)
            if (item.equals(productId)) {
                quantity++;
            }
        }
        return quantity;
    }

    public void deleteOrder(int id) {
        String delete = "DELETE FROM orders WHERE id = ?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }

    public void updateOrderStatus(int id, String newStatus) {
        String update = "UPDATE orders SET status = ? WHERE id = ?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(update)) {
            prSt.setString(1, newStatus);
            prSt.setInt(2, id);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }

    public void promote_to_manager(int id) {
        String insert = "INSERT INTO employees (id) VALUES (?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(id));

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error promoting: " + e.getMessage());
        }
    }

    public void promote_to_admin(int id) {
        String insert = "INSERT INTO administrators (id) VALUES (?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(id));

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error promoting: " + e.getMessage());
        }
    }

    // Custom exception class for insufficient stock errors
    public static class InsufficientStockException extends Exception {
        public InsufficientStockException(String message) {
            super(message);
        }
    }
}
