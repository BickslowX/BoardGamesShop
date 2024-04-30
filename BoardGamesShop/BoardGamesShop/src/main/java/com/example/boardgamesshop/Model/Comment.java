package com.example.boardgamesshop.Model;

import java.util.Scanner;

public class Comment {

    private int id;
    private int author_id;
    private int product_id;
    private String author_name;
    private String product_name;
    private String content;
    private int rating; // Can be optional for comments without ratings
    private int parent_comment_id; // For nested comments

    public Comment(int id, int author_id, int product_id, String author_name, String product_name, String content, int rating, int parent_comment_id) {
        this.id = id;
        this.author_id = author_id;
        this.product_id = product_id;
        this.author_name = author_name;
        this.product_name = product_name;
        this.content = content;
        this.rating = rating;
        this.parent_comment_id = parent_comment_id;
    }
    public Comment(int author_id, int product_id, String content, int rating) {
        this.author_id = author_id;
        this.product_id = product_id;
        this.content = content;
        this.rating = rating;
    }

    @Override
    public String toString() {
        StringBuilder commentString = new StringBuilder();
        if (parent_comment_id > 0) {
            commentString.append("Parent Comment ID: ").append(parent_comment_id).append("\n");
        }
        commentString.append("Comment ID: ").append(id).append("\n");
        commentString.append("Author: ").append(author_name).append("\n");
        commentString.append("Content: ").append(content).append("\n");
        if (rating > 0) {
            commentString.append("Rating: ").append(rating).append(" ★").append("\n");
        }
        return commentString.toString();
    }
    public static Comment fromString(String commentString) {
        // Use a Scanner to tokenize the String
        Scanner scanner = new Scanner(commentString);

        int id = 0;
        int authorId = 0;
        int productId = 0;
        String authorName = null;
        String productName = null;
        String content = null;
        int rating = 0;
        int parentCommentId = 0;

        boolean readingContent = false; // Flag to indicate reading content

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Comment ID: ")) {
                id = Integer.parseInt(line.substring(13));
            } else if (line.startsWith("Author: ")) {
                authorName = line.substring(8);
            } else if (line.startsWith("Product: ")) {
                productName = line.substring(9);
            } else if (line.startsWith("Rating: ")) {
                String[] parts = line.split(" ");
                rating = Integer.parseInt(parts[1]);
            } else if (line.startsWith("Parent Comment ID: ")) {
                parentCommentId = Integer.parseInt(line.substring(20));
            } else if (line.isEmpty()) {
                readingContent = false; // Empty line indicates end of content
            } else {
                if (!readingContent) {
                    content = line; // First line after labels is considered content
                    readingContent = true;
                } else {
                    content += "\n" + line; // Append subsequent lines to content
                }
            }
        }
        return new Comment(id, authorId, productId, authorName, productName, content, rating, parentCommentId);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public int getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(int parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }
}
