package com.example.boardgamesshop.Model;

import java.util.Comparator;

public class CommentComparator implements Comparator<Comment> {

    @Override
    public int compare(Comment comment1, Comment comment2) {
        if (comment1.getParent_comment_id() == 0 && comment2.getParent_comment_id() != 0) {
            return -1; // Parent comment comes before reply
        } else if (comment1.getParent_comment_id() != 0 && comment2.getParent_comment_id() == 0) {
            return 1; // Reply comes after parent comment
        } else {
            return Integer.compare(comment1.getParent_comment_id(), comment2.getParent_comment_id());
        }
    }
}