package com.example.boardgamesshop.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User {

    private List<Order> orderHistory;
    private List<Product> wishlist;

    public Customer(int id, String name, String surname, String contactInfo, Date dateOfBirth, String password) {
        super(id, name, surname, contactInfo, dateOfBirth, password);
        this.orderHistory = new ArrayList<>();
        this.wishlist = new ArrayList<>();
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public List<Product> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Product> wishlist) {
        this.wishlist = wishlist;
    }

    public void rateProduct(Product product, int rating) {
        // Implement logic to add rating to the product
        product.addRating(rating);
    }

    public void chatWithManager(Order order) {
        // Implement logic to initiate a chat with the manager assigned to the order (e.g., opening a chat window)
        System.out.println("Initiating chat with manager for order #" + order.getId());
    }

    public void cancelOrder(Order order) {
        // Implement logic to cancel the order if it's still pending
        if (order.getStatus() == Order.Status.PENDING) {
            order.cancelOrder();
        } else {
            System.out.println("Order #" + order.getId() + " cannot be cancelled as it's already " + order.getStatus());
        }
    }

    public void viewPurchaseHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("You have no past orders.");
        } else {
            for (Order order : orderHistory) {
                System.out.println(order); // Order class should have a toString() method for displaying details
            }
        }
    }

    // Add order to customer's history
    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    // Add product to customer's wishlist
    public void addToWishlist(Product product) {
        wishlist.add(product);
    }
}




