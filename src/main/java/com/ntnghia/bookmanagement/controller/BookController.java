package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.payload.request.BookDto;
import com.ntnghia.bookmanagement.payload.response.PaginationResponse;
import com.ntnghia.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping()
    public PaginationResponse getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.getAll(keyword, orderBy, order, page, size);
    }

    @GetMapping("/enabled")
    public PaginationResponse getEnabledBook(@RequestParam(required = false) String keyword,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return bookService.getEnabledBook(keyword, page, size);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public PaginationResponse getByUserId(@RequestParam(required = false) String keyword,
                                          @PathVariable(value = "userId") int userId,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return bookService.findByUserId(keyword, userId, page, size);
    }

    @GetMapping(value = "/search")
    public PaginationResponse getByKeyword(@RequestParam("keyword") String keyword,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.findByKeyword(keyword, page, size);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookDto post(@PathVariable(value = "userId") int userId,
                        @Valid @RequestBody BookDto bookDto
    ) {
        return bookService.saveBook(userId, bookDto);
    }

    @PutMapping("/{bookId}/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookDto put(@PathVariable(value = "bookId") int bookId,
                       @PathVariable(value = "userId") int userId,
                       @Valid @RequestBody BookDto bookDto
    ) {
        return bookService.updateBook(bookId, userId, bookDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
