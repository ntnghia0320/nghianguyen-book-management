package com.ntnghia.bookmanagement.repository;

import com.ntnghia.bookmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByEnabled(Boolean enabled, Pageable paging);

    Page<Book> findByEnabledAndTitleContainsIgnoreCaseOrEnabledAndAuthorContainsIgnoreCase(
            Boolean enabled, String title, Boolean enabled1, String author, Pageable paging);

    List<Book> findByTitle(String Title);

    List<Book> findByAuthor(String Author);

    Page<Book> findByUserId(int userId, Pageable paging);

    Page<Book> findByUserIdAndTitleContainsIgnoreCaseOrUserIdAndAuthorContainsIgnoreCase(
            int userId, String title, int userId1, String author, Pageable paging);

    Page<Book> findByTitleContainsIgnoreCaseOrAuthorContainsIgnoreCase(String title, String author, Pageable paging);
}
