package com.ntnghia.bookmanagement.service;

import com.ntnghia.bookmanagement.payload.request.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto findById(int id);

    List<CommentDto> findByBookId(int bookId);

    CommentDto saveComment(int userId, int bookId, CommentDto commentDto);

    CommentDto updateComment(int commentId, int userId, int bookId, CommentDto commentDto);

    void deleteComment(int id);
}
