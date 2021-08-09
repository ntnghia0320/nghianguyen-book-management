package com.ntnghia.bookmanagement.service;

import com.ntnghia.bookmanagement.payload.request.BookDto;
import com.ntnghia.bookmanagement.payload.response.PaginationResponse;

public interface BookService {
    PaginationResponse getAll(String keyword, String orderBy, String order, int page, int size);

    PaginationResponse getEnabledBook(String keyword, int page, int size);

    BookDto findById(int id);

    PaginationResponse findByUserId(String keyword, int userId, int page, int size);

    PaginationResponse findByKeyword(String keyword, int page, int size);

    BookDto saveBook(int userId, BookDto bookDto);

    BookDto updateBook(int bookId, int userId, BookDto bookDto);

    void deleteBook(int id);
}
