package com.example.boardgamesshop.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED
    }

    private int id;
    private Customer customer;
    private List<Product> items;
    private double total;
    private Status status;
    private Date orderDate;

    public Order(int id, Customer customer, Date orderDate) {
        this.id = id;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.status = Status.PENDING;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity > 0) {
            items.add(product);
            total += product.getPrice() * quantity;
        } else {
            System.out.println("Error: Cannot add negative quantity to order.");
        }
    }

    public double calculateTotal() {
        return total;
    }

    public void confirmOrder() {
        if (status == Status.PENDING) {
            status = Status.CONFIRMED;
            System.out.println("Order #" + id + " confirmed.");
        } else {
            System.out.println("Order #" + id + " is already " + status + " and cannot be confirmed again.");
        }
    }

    public void cancelOrder() {
        if (status == Status.PENDING) {
            status = Status.CANCELLED;
            System.out.println("Order #" + id + " cancelled.");
        } else {
            System.out.println("Order #" + id + " is already " + status + " and cannot be cancelled.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");
        sb.append("Order Date: ").append(orderDate).append("\n");
        sb.append("Items:\n");
        for (Product product : items) {
            sb.append("- ").append(product.getName()).append("\n");
        }
        sb.append("Total: ").append(total).append("\n");
        sb.append("Status: ").append(status).append("\n");
        return sb.toString();
    }
}


