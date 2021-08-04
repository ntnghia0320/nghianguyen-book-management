package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.payload.request.CommentDto;
import com.ntnghia.bookmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public CommentDto getById(@PathVariable int id) {
        return commentService.findById(id);
    }

    @GetMapping("/book/{bookId}")
    public List<CommentDto> getByBookId(@PathVariable int bookId) {
        return commentService.findByBookId(bookId);
    }

    @PostMapping("/{userId}/{bookId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CommentDto post(@PathVariable(value = "bookId") int bookId,
                           @PathVariable(value = "userId") int userId,
                           @Valid @RequestBody CommentDto commentDto) {
        return commentService.saveComment(userId, bookId, commentDto);
    }

    @PutMapping("/{commentId}/{userId}/{bookId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CommentDto put(@PathVariable(value = "commentId") int commentId,
                          @PathVariable(value = "bookId") int bookId,
                          @PathVariable(value = "userId") int userId,
                          @Valid @RequestBody CommentDto commentDto) {
        return commentService.updateComment(commentId, userId, bookId, commentDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        commentService.deleteComment(id);
    }
}
