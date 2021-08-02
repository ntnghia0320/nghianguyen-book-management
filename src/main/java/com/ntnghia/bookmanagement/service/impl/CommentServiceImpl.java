package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.Comment;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.repository.BookRepository;
import com.ntnghia.bookmanagement.repository.CommentRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Comment findById(int id) {
        if (isCommentIdExist(id)) {
            return commentRepository.findById(id).get();
        }

        throw new NotFoundException(String.format("Comment id %d is not found", id));
    }

    @Override
    public List<Comment> findByUserId(int userId) {
        if (isUserIdExist(userId)) {
            return commentRepository.findByUserId(userId);
        }

        throw new NotFoundException(String.format("User id %d is not found", userId));
    }

    @Override
    public List<Comment> findByBookId(int bookId) {
        if (isBookIdExist(bookId)) {
            return commentRepository.findByBookId(bookId);
        }

        throw new NotFoundException(String.format("Book id %d is not found", bookId));
    }

    @Override
    public Comment saveComment(int userId, int bookId, Comment comment) {
        if (!isUserIdExist(userId)) {
            throw new NotFoundException(String.format("User id %d is not found", userId));
        } else if (!isBookIdExist(bookId)) {
            throw new NotFoundException(String.format("Book id %d is not found", bookId));
        }

        comment.setUser(userRepository.findById(userId).get());
        comment.setBook(bookRepository.findById(bookId).get());

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(int id, Comment comment) {
        if (isCommentIdExist(id)) {
            comment.setId(id);

            return commentRepository.save(comment);
        }

        throw new NotFoundException(String.format("Comment id %d is not found", id));
    }

    @Override
    public void deleteComment(int id) {
        if (isCommentIdExist(id)) {
            commentRepository.deleteById(id);
        }
        throw new NotFoundException(String.format("Comment id %d is not found", id));
    }

    private boolean isCommentIdExist(int id) {
        return commentRepository.existsById(id);
    }

    private boolean isUserIdExist(int id) {
        return userRepository.existsById(id);
    }

    private boolean isBookIdExist(int id) {
        return bookRepository.existsById(id);
    }
}
