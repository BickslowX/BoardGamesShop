package com.example.boardgamesshop.Controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.example.boardgamesshop.DB.DBHandler;
import com.example.boardgamesshop.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrationForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker reg_birthday_field;

    @FXML
    private Button reg_button;

    @FXML
    private TextField reg_contact_info_field;

    @FXML
    private TextField reg_name_field;

    @FXML
    private PasswordField reg_password_check_field;

    @FXML
    private PasswordField reg_password_field;

    @FXML
    private TextField reg_surname_field;

    @FXML
    void initialize() {
        reg_button.setOnAction(event -> {
            String regLogin = reg_name_field.getText().trim();
            String regPassword = reg_password_field.getText().trim();
            String regPasswordCheck = reg_password_check_field.getText().trim();
            String regSurname = reg_surname_field.getText().trim();
            String regContactInfo = reg_contact_info_field.getText().trim();
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String regBirthDate = reg_birthday_field.getValue().format(pattern);

            if((regPassword.equals(regPasswordCheck))&&!regLogin.isEmpty() && !regPassword.isEmpty() && !regPasswordCheck.isEmpty() && !regSurname.isEmpty() && !regContactInfo.isEmpty())
            {
                DBHandler dbHandler = new DBHandler();
                dbHandler.AddUser(regLogin,regPassword,regSurname,regContactInfo,regBirthDate);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Congrats");
                alert.setContentText("Now you are registered");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, wrong date");
                alert.setContentText("Enter valid information and try again");
                alert.showAndWait();
            }
        });

    }

}
