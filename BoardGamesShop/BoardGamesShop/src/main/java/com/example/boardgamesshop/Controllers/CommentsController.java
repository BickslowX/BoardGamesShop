package com.example.boardgamesshop.Controllers;

import java.net.URL;
import java.sql.*;
import java.util.*;

import com.example.boardgamesshop.DB.DBHandler;
import com.example.boardgamesshop.Model.Comment;
import com.example.boardgamesshop.Model.CommentComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

public class CommentsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea comment_textarea;

    @FXML
    private Label header_label;

    @FXML
    private Button leave_review_button;

    @FXML
    private Rating rating_stars;

    @FXML
    private ListView<Comment> reviews_listview;

    private static String CurrentUserId;
    private static String CurrentProductId;
    private static String CurrentProductName;

    public void SetCurrentProduct(String CurrentUserId, String CurrentProductId, String CurrentProductName) {
        this.CurrentUserId = CurrentUserId;
        CommentsController.CurrentProductId = CurrentProductId;
        CommentsController.CurrentProductName = CurrentProductName;
    }

    @FXML
    void initialize() {
        header_label.setText(CurrentProductName + " Reviews");
        try {
            loadComments();
        }
        catch ( SQLException | ClassNotFoundException e) {}

        leave_review_button.setOnAction(event -> {
            if(leave_review_button.getText().equals("Leave Review"))
            {
                Comment comment = new Comment(Integer.parseInt(CurrentUserId),Integer.parseInt(CurrentProductId),comment_textarea.getText(),(int)rating_stars.getRating());
                DBHandler dbHandler = new DBHandler();
                dbHandler.AddReview(comment);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Review was added successfully");
                comment_textarea.setText("");
                rating_stars.setRating(5);
                try {
                    loadComments();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                alert.showAndWait();
            }
            else if(leave_review_button.getText().equals("Leave Reply"))
            {
                int parent = Integer.parseInt(comment_textarea.getText().trim().split(" ")[0].replace("@",""));

                String currentText = comment_textarea.getText();
                String newText = currentText.replaceAll("@\\S+", "").trim();
                comment_textarea.setText(newText);

                DBHandler dbHandler = new DBHandler();
                dbHandler.AddReply(Integer.parseInt(CurrentUserId),Integer.parseInt(CurrentProductId),comment_textarea.getText(),parent);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Success");
                alert.setContentText("Review was added successfully");
                comment_textarea.setText("");
                rating_stars.setRating(5);
                try {
                    loadComments();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Error, product was not deleted successfully");
                alert.setContentText("Check information and try again");
                alert.showAndWait();
            }
        });

    }

    private void loadComments() throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        Connection con = dbHandler.getDbConnection();
        ObservableList<Comment> comments = FXCollections.observableArrayList();

        try (Statement stmt = con.createStatement()) {
            // Execute the query assuming your table's column names are accurate

            String query = "SELECT \n" +
                    "    t.*, \n" +
                    "    u.name AS AuthorName, \n" +
                    "    u.surname AS AuthorSurname, \n" +
                    "    p.name AS ProductName\n" +
                    "FROM comments t \n" +
                    "INNER JOIN users u ON t.author_id = u.id\n" +
                    "INNER JOIN products p ON t.product_id = p.id\n" +
                    "WHERE product_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, CurrentProductId);
            ResultSet commentList = pstmt.executeQuery();
            // Extract data and create Prod objects
            while (commentList.next()) {
                int id = commentList.getInt("id");
                int author_id = commentList.getInt("author_id");
                int product_id = commentList.getInt("product_id");
                String content = commentList.getString("content");
                String author_name = commentList.getString("AuthorName") + " "+ commentList.getString("AuthorSurname");
                String product_name = commentList.getString("ProductName");
                int rating = 0;
                int parent_comment_id = 0;
                try {
                     rating = commentList.getInt("rating");
                    parent_comment_id = commentList.getInt("parent_comment_id");
                }
                catch (SQLException e) {}

                comments.add(new Comment(id, author_id, product_id, author_name, product_name, content, rating, parent_comment_id));
            }

            // Set the items of the TableView with the populated product list
            comments = sortComments(comments);
            reviews_listview.setItems(comments);
        } catch (SQLException e) {
            // Handle the SQLException appropriately (e.g., display error message)
            System.err.println("Error loading products: " + e.getMessage());
        } finally {
            // Close the connection (assuming it's managed by DBHandler)
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " +  e.getMessage());
            }
        }
    }

    public ObservableList<Comment> sortComments(ObservableList<Comment> unsortedComments) {
        ObservableList<Comment> sortedComments = FXCollections.observableArrayList();

        // Separate parent comments and replies (optional for efficiency)
        List<Comment> parentComments = new ArrayList<>();
        List<Comment> replyComments = new ArrayList<>();

        for (Comment comment : unsortedComments) {
            if (comment.getParent_comment_id() == 0) {
                parentComments.add(comment);
            } else {
                replyComments.add(comment);
            }
        }

        // Sort parent comments
        Collections.sort(parentComments, new CommentComparator());

        // Sort replies based on their parent ID (optional for efficiency)
        Map<Integer, List<Comment>> repliesByParent = new HashMap<>();
        for (Comment reply : replyComments) {
            int parentId = reply.getParent_comment_id();
            repliesByParent.computeIfAbsent(parentId, k -> new ArrayList<>()).add(reply);
        }

        // Recursively add sorted replies to parent comments (optional for nested replies)
        for (Comment parentComment : parentComments) {
            sortedComments.add(parentComment);
            List<Comment> sortedReplies = sortReplies(repliesByParent.get(parentComment.getId()));
            sortedComments.addAll(sortedReplies);
        }

        return sortedComments;
    }

    // Helper method for recursive sorting of replies (optional for nested replies)
    private List<Comment> sortReplies(List<Comment> replies) {
        if (replies == null || replies.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.sort(replies, new CommentComparator());
        return replies;
    }



    public void handleCommentClick(MouseEvent event) {
        if (event.getClickCount() == 1 && reviews_listview.getSelectionModel().getSelectedItem() != null) {
            // Get the selected comment object
            Comment selectedComment = reviews_listview.getSelectionModel().getSelectedItem();
            String currText = comment_textarea.getText();
            String nText = currText.replaceAll("@\\S+", "").trim();
            comment_textarea.setText(nText);
            // Get the current text in the comment_textarea
            String currentText = comment_textarea.getText();

            String newText = ("@" + selectedComment.getId() + " ") + (currentText);
            rating_stars.setRating(0);
            leave_review_button.setText("Leave Reply");

            // Update the TextArea with the new text
            comment_textarea.setText(newText);
        }
    }

    public void stars_clicked(MouseEvent event) {
        leave_review_button.setText("Leave Review");
        String currentText = comment_textarea.getText();

        // Remove all text between "@" and the next space (if present)
        String newText = currentText.replaceAll("@\\S+", "").trim();
        comment_textarea.setText(newText);
    }

}
