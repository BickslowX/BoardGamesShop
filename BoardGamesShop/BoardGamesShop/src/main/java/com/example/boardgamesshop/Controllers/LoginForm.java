package com.example.boardgamesshop.Controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.*;
import com.example.boardgamesshop.DB.DBHandler;
import com.example.boardgamesshop.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button log_in_button;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField pswField;

    @FXML
    private Button register_button;

    @FXML
    void initialize() {
        log_in_button.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = pswField.getText().trim();
            String current_user_id = "";
            if(!loginText.isEmpty() && !loginPassword.isEmpty()) {
                boolean log = loginUser(loginText,loginPassword);
                if(log)
                {
                    log_in_button.getScene().getWindow().hide();

                    DBHandler dbHandler = new DBHandler();
                    String query = "SELECT id FROM users WHERE name = ? AND password_hash = ?";
                    try {
                        Connection con = dbHandler.getDbConnection();
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, loginText);
                        pstmt.setString(2, loginPassword);
                        ResultSet usersList = pstmt.executeQuery();
                        usersList.next();
                        current_user_id = usersList.getString("id");
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/boardgamesshop/main-form.fxml"));

                    try {
                        loader.load();
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("userid.txt", false))) {
                                writer.write(current_user_id);
                            writer.newLine();
                        } catch (IOException e) {
                        throw new RuntimeException(e);
                    }




                    Parent root = loader.getRoot();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, empty login or password");
                alert.setContentText("Enter valid information and try again");
                alert.showAndWait();
            }
        });
        register_button.setOnAction(event -> {
            register_button.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/boardgamesshop/Registration-form.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });


    }

    private boolean loginUser(String loginText, String loginPassword) {
        DBHandler dbHandler = new DBHandler();
        User user = new User();
        user.setName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while(true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if(counter>=1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
