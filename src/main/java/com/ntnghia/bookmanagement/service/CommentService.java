package com.ntnghia.bookmanagement.service;

import com.ntnghia.bookmanagement.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment findById(int id);

    List<Comment> findByUserId(int userId);

    List<Comment> findByBookId(int bookId);

    Comment saveComment(int userId, int bookId, Comment comment);

    Comment updateComment(int id, Comment comment);

    void deleteComment(int id);
}
