package com.example.boardgamesshop.Model;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private String imagePath;
    private List<Comment> reviews;
    private double averageRating;
    private int quantity; // Added quantity attribute

    public Product(int id, String name, String description, double price, String imagePath, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.reviews = new ArrayList<>();
        this.averageRating = 0.0;
        this.quantity = quantity; // Initialize quantity
    }

    // Getters and Setters (including for quantity)

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Comment> getReviews() {
        return reviews;
    }

    public void setReviews(List<Comment> reviews) {
        this.reviews = reviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void addRating(int rating) {
        //this.averageRating = averageRating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addComment(Comment comment) {
        reviews.add(comment);
        updateAverageRating();
    }

    private void updateAverageRating() {
        if (reviews.isEmpty()) {
            averageRating = 0.0;
        } else {
            double totalRating = 0.0;
            for (Comment review : reviews) {
                totalRating += review.getRating();
            }
            averageRating = totalRating / reviews.size();
        }
    }
}
