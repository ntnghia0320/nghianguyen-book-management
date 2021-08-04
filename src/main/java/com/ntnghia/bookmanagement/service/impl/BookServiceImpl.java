package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.Book;
import com.ntnghia.bookmanagement.exception.BadRequestException;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.payload.request.BookDto;
import com.ntnghia.bookmanagement.payload.request.UserDto;
import com.ntnghia.bookmanagement.repository.BookRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<BookDto> getAll() {
        return convertAllBookEntityToBookDto(bookRepository.findAll());
    }

    @Override
    public List<BookDto> getEnabledBook() {
        return convertAllBookEntityToBookDto(bookRepository.findByEnabled(true));
    }

    @Override
    public BookDto findById(int id) {
        if (isBookIdExist(id)) {
            Book bookEntity = bookRepository.findById(id).get();
            return convertBookEntityToBookDto(bookEntity);
        }

        throw new NotFoundException(String.format("Book id %d not found", id));
    }

    @Override
    public List<BookDto> findByUserId(int userId) {
        return convertAllBookEntityToBookDto(bookRepository.findByUserId(userId));
    }

    @Override
    public List<BookDto> findByKeyword(String keyword) {
        return convertAllBookEntityToBookDto(
                bookRepository.findByTitleContainsOrAuthorContains(keyword, keyword)
        );
    }

    @Override
    public BookDto saveBook(int userId, BookDto bookDto) {
        Book bookEntity = convertBookDtoToBookEntity(bookDto);

        if (isBookExist(bookEntity)) {
            throw new BadRequestException("This book is already exists");
        }

        if (!isUserIdExist(userId)) {
            throw new NotFoundException(String.format("User id %d not found", userId));
        }

        bookEntity.setUser(userRepository.findById(userId).get());
        bookEntity = bookRepository.save(bookEntity);

        return convertBookEntityToBookDto(bookEntity);
    }

    @Override
    public BookDto updateBook(int bookId, int userId, BookDto bookDto) {
        Book bookEntity = convertBookDtoToBookEntity(bookDto);

        if (!isBookIdExist(bookId)) {
            throw new NotFoundException(String.format("Book id %d not found", bookId));
        }

        if (bookEntity.equals(bookRepository.findById(bookId).get())) {
            throw new BadRequestException("This book is not change");
        }

        if (isBookExist(bookEntity)) {
            throw new BadRequestException("This book is already exists");
        }

        bookEntity.setId(bookId);
        bookEntity.setUser(userRepository.findById(userId).get());

        bookEntity = bookRepository.save(bookEntity);

        return convertBookEntityToBookDto(bookEntity);
    }

    @Override
    public void deleteBook(int id) {
        if (!isBookIdExist(id)) {
            throw new NotFoundException(String.format("Book id %d not found", id));
        }

        bookRepository.deleteById(id);
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

    private BookDto convertBookEntityToBookDto(Book bookEntity) {
        UserDto userDto = modelMapper.map(bookEntity.getUser(), UserDto.class);

        BookDto bookDto = modelMapper.map(bookEntity, BookDto.class);
        bookDto.setUser(userDto);

        return bookDto;
    }

    private Book convertBookDtoToBookEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    private List<BookDto> convertAllBookEntityToBookDto(List<Book> books) {
        return books.stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }
}
