package com.example.boardgamesshop.Model;

public class Comment {

    private int id;
    private User author;
    private Product product;
    private String content;
    private int rating; // Can be optional for comments without ratings
    private Comment parentComment; // For nested comments

    public Comment(User author, Product product, String content) {
        this.author = author;
        this.product = product;
        this.content = content;
    }

    public Comment(User author, Product product, String content, int rating) {
        this(author, product, content); // Call the other constructor
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean isNestedComment() {
        return parentComment != null;
    }

    public String getAuthorName() {
        return author.getName();
    }
}
