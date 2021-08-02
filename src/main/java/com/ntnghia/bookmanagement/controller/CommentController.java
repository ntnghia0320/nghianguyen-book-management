package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.entity.Comment;
import com.ntnghia.bookmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public Comment getById(@PathVariable int id) {
        return commentService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getByUserId(@PathVariable int userId) {
        return commentService.findByUserId(userId);
    }

    @GetMapping("/book/{bookId}")
    public List<Comment> getByBookId(@PathVariable int bookId) {
        return commentService.findByBookId(bookId);
    }

    @PostMapping("/{userId}/{bookId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Comment post(@PathVariable(value = "bookId") int bookId,
                        @PathVariable(value = "userId") int userId,
                        @Valid @RequestBody Comment comment) {
        return commentService.saveComment(userId, bookId, comment);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Comment put(@PathVariable int id, @Valid @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        commentService.deleteComment(id);
    }
}
