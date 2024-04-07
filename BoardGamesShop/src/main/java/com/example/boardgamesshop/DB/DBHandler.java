package com.example.boardgamesshop.DB;

import com.example.boardgamesshop.Model.User;

import java.sql.*;

public class DBHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Connection dbConnection;
        Class.forName("com.mysql.jdbc.Driver");

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
