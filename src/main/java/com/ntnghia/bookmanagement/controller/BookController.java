package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.payload.request.BookDto;
import com.ntnghia.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping()
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/enabled-book")
    public List<BookDto> getEnabledBook() {
        return bookService.getEnabledBook();
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @GetMapping("/user/{id}")
    public List<BookDto> getByUserId(@PathVariable int id) {
        return bookService.findByUserId(id);
    }

    @GetMapping(value = "/search")
    public List<BookDto> getByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.findByKeyword(keyword);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookDto post(@PathVariable(value = "userId") int userId,
                        @Valid @RequestBody BookDto bookDto) {
        return bookService.saveBook(userId, bookDto);
    }

    @PutMapping("/{bookId}/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookDto put(@PathVariable(value = "bookId") int bookId,
                       @PathVariable(value = "userId") int userId,
                       @Valid @RequestBody BookDto bookDto) {
        return bookService.updateBook(bookId, userId, bookDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
