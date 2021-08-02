package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.Book;
import com.ntnghia.bookmanagement.exception.BadRequestException;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.repository.BookRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getEnabledBook() {
        return bookRepository.findByEnabled(true);
    }

    @Override
    public Book findById(int id) {
        if (isBookIdExist(id)) {
            return bookRepository.findById(id).get();
        }

        throw new NotFoundException(String.format("Book id %d not found", id));
    }

    @Override
    public List<Book> findByUserId(int userId) {
        return bookRepository.findByUserId(userId);
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return bookRepository.findByTitleContainsOrAuthorContains(keyword, keyword);
    }

    @Override
    public Book saveBook(int userId, Book book) {
        if (isBookExist(book)) {
            throw new BadRequestException("This book is already exists");
        }

        if (!isUserIdExist(userId)) {
            throw new NotFoundException(String.format("User id %d not found", userId));
        }

        book.setUser(userRepository.findById(userId).get());

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(int id, Book book) {
        if (!isBookIdExist(id)) {
            throw new NotFoundException(String.format("Book id %d not found", id));
        }

        if (isBookExist(book)) {
            throw new BadRequestException("This book is already exists");
        }

        book.setId(id);

        return book;
    }

    @Override
    public void deleteBook(int id) {
        if (isBookIdExist(id)) {
            bookRepository.deleteById(id);
        }

        throw new NotFoundException(String.format("Book id %d not found", id));
    }

    private boolean isBookIdExist(int id) {
        return bookRepository.existsById(id);
    }

    private boolean isUserIdExist(int id) {
        return userRepository.existsById(id);
    }

    private boolean isBookExist(Book book) {
        return !bookRepository.findByTitle(book.getTitle()).isEmpty()
                && !bookRepository.findByAuthor(book.getAuthor()).isEmpty();
    }
}
