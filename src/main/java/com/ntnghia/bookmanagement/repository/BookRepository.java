package com.ntnghia.bookmanagement.repository;

import com.ntnghia.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByEnabled(Boolean enabled);

    List<Book> findByTitle(String Title);

    List<Book> findByAuthor(String Author);

    List<Book> findByUserId(int userId);

    List<Book> findByTitleContainsOrAuthorContains(String title, String author);
}
