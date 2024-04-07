package com.example.boardgamesshop.Model;

import java.util.Date;

public class Employee extends User {

    private String department;
    private double salary;

    public Employee(int id, String name, String surname, String contactInfo, Date dateOfBirth, String password, String department, double salary) {
        super(id, name, surname, contactInfo, dateOfBirth, password);
        this.department = department;
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Implement functionalities specific to employee roles (placeholder for now)
    public void processOrder(Order order) {
        System.out.println("Employee " + this.getName() + " is processing order #" + order.getId());
        // Implement logic to process the order (e.g., confirm or reject)
    }
}
