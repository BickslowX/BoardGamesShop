package com.example.boardgamesshop.Model;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private int id;
    private String location;
    private List<Product> stock;

    public Warehouse(int id, String location) {
        this.id = id;
        this.location = location;
        this.stock = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Product> getStock() {
        return stock;
    }

    public void setStock(List<Product> stock) {
        this.stock = stock;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity > 0) {
            // Check if product already exists in stock
            boolean found = false;
            for (Product item : stock) {
                if (item.getId() == product.getId()) {
                    item.setQuantity(item.getQuantity() + quantity); // Update quantity if found
                    found = true;
                    break;
                }
            }
            if (!found) {
                product.setQuantity(quantity); // Set quantity for new product
                stock.add(product);
            }
        } else {
            System.out.println("Error: Cannot add negative quantity to stock.");
        }
    }

    public void removeProduct(Product product, int quantity) {
        if (quantity > 0) {
            for (Product item : stock) {
                if (item.getId() == product.getId()) {
                    if (item.getQuantity() >= quantity) {
                        item.setQuantity(item.getQuantity() - quantity);
                        if (item.getQuantity() == 0) {
                            stock.remove(item);
                        }
                        return;
                    } else {
                        System.out.println("Insufficient stock for product: " + product.getName());
                        return;
                    }
                }
            }
            System.out.println("Product not found in warehouse stock.");
        } else {
            System.out.println("Error: Cannot remove negative quantity from stock.");
        }
    }

    // Helper method to check if product is in stock (assuming sufficient quantity by default)
    public boolean hasProduct(Product product) {
        for (Product item : stock) {
            if (item.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }
}


