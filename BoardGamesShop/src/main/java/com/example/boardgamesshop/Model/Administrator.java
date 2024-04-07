package com.example.boardgamesshop.Model;

import java.util.Date;

public class Administrator extends User {

    public Administrator(int id, String name, String surname, String contactInfo, Date dateOfBirth, String password) {
        super(id, name, surname, contactInfo, dateOfBirth, password);
    }

    // Getters and Setters (omitted for brevity)

    public void createUser(User user) {
        // Implement logic to create a new user in the system (e.g., persist to database)
        System.out.println("Administrator created user: " + user.getName());
    }

    public void updateUser(User user) {
        // Implement logic to update user information in the system (e.g., persist changes to database)
        System.out.println("Administrator updated user: " + user.getName());
    }

    public void deleteUser(User user) {
        // Implement logic to delete a user from the system (e.g., remove from database)
        System.out.println("Administrator deleted user: " + user.getName());
    }

    // Implement functionalities specific to administrator privileges (placeholder for now)
    public void manageProducts() {
        System.out.println("Administrator is managing products...");
        // Implement logic to access product management functionalities (e.g., adding, editing, deleting products)
    }

    public void manageUsers() {
        System.out.println("Administrator is managing users...");
        // Implement logic to access user management functionalities (e.g., viewing all users, searching for specific users)
    }
}

