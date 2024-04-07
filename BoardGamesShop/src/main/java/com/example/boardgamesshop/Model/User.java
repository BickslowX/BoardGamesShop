package com.example.boardgamesshop.Model;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String surname;
    private String contactInfo;
    private Date dateOfBirth;
    private String password;

    public User(int id, String name, String surname, String contactInfo, Date dateOfBirth, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.contactInfo = contactInfo;
        this.dateOfBirth = dateOfBirth;
        this.password = hashPassword(password);
    }

    public User() {

    }

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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    // Omit setter for passwordHash as it should not be modified after hashing
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserInfo() {
        return String.format("Name: %s %s\nContact Info: %s\nDate of Birth: %s", name, surname, contactInfo, dateOfBirth);
    }

    // Helper methods for password hashing (replace with secure hashing)
    private static String hashPassword(String password) {
        // Implement logic to hash the password using a secure hashing algorithm (e.g., bcrypt)
        // This example uses a placeholder function
        return password;
    }

    private static boolean verifyPassword(String password, String hashedPassword) {
        // Implement logic to verify the password against the stored hash
        // This example uses a placeholder function
        return hashedPassword.startsWith("hashed_") && hashedPassword.substring(7).equals(password);
    }
}
