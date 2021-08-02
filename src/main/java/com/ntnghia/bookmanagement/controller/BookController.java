package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.entity.Book;
import com.ntnghia.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping()
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/enabled-book")
    public List<Book> getEnabledBook() {
        return bookService.getEnabledBook();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @GetMapping("/user/{id}")
    public List<Book> getByUserId(@PathVariable int id) {
        return bookService.findByUserId(id);
    }

    @GetMapping(value = "/search", params = "keyword")
    public List<Book> getByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.findByKeyword(keyword);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Book post(@PathVariable(value = "userId") int userId,
                     @Valid @RequestBody Book book) {
        return bookService.saveBook(userId, book);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Book put(@PathVariable int id, @Valid @RequestBody Book post) {
        return bookService.updateBook(id, post);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
