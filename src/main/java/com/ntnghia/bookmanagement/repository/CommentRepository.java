package com.ntnghia.bookmanagement.repository;

import com.ntnghia.bookmanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBookIdOrderByCreatedAtDesc(int bookId);
}
