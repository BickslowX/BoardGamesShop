package com.example.boardgamesshop.DB;

import com.example.boardgamesshop.Model.User;

import java.sql.*;

public class DBHandler extends Configs {

    Connection dbConnection;

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
    public ResultSet getProducts()
    {
        ResultSet resSet = null;
        String select = "SELECT * FROM products";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
}
