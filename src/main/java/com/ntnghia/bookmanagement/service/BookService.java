package com.ntnghia.bookmanagement.service;

import com.ntnghia.bookmanagement.payload.request.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    List<BookDto> getEnabledBook();

    BookDto findById(int id);

    List<BookDto> findByUserId(int id);

    List<BookDto> findByKeyword(String keyword);

    BookDto saveBook(int userId, BookDto bookDto);

    BookDto updateBook(int bookId, int userId, BookDto bookDto);

    void deleteBook(int id);
}
