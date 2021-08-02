package com.ntnghia.bookmanagement.service;

import com.ntnghia.bookmanagement.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    List<Book> getEnabledBook();

    Book findById(int id);

    List<Book> findByUserId(int id);

    List<Book> findByKeyword(String keyword);

    Book saveBook(int userId, Book book);

    Book updateBook(int id, Book book);

    void deleteBook(int id);
}
