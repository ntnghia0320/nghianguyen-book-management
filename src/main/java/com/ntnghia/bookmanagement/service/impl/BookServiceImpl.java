package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.Book;
import com.ntnghia.bookmanagement.exception.BadRequestException;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.payload.request.BookDto;
import com.ntnghia.bookmanagement.payload.request.UserDto;
import com.ntnghia.bookmanagement.payload.response.PaginationResponse;
import com.ntnghia.bookmanagement.repository.BookRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PaginationResponse getAll(String keyword, String orderBy, String order, int page, int size) {
        Pageable paging;

        if (order.equals("asc")) {
            paging = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        } else if (order.equals("desc")) {
            paging = PageRequest.of(page, size, Sort.by(orderBy).descending());
        } else {
            throw new BadRequestException("Type of order not found");
        }

        Page<Book> bookPage;

        if (keyword == null) {
            bookPage = bookRepository.findAll(paging);
        } else {
            bookPage = bookRepository
                    .findByTitleContainsIgnoreCaseOrAuthorContainsIgnoreCase(keyword, keyword, paging);
        }

        return new PaginationResponse(bookPage.getTotalElements(),
                convertAllBookEntityToBookDto(bookPage.getContent()));
    }

    @Override
    public PaginationResponse getEnabledBook(String keyword, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> bookPage;
        if (keyword == null) {
            bookPage = bookRepository.findByEnabled(true, paging);
        } else {
            bookPage = bookRepository.findByEnabledAndTitleContainsIgnoreCaseOrEnabledAndAuthorContainsIgnoreCase(
                    true, keyword, true, keyword, paging);
        }

        return new PaginationResponse(bookPage.getTotalElements(),
                convertAllBookEntityToBookDto(bookPage.getContent()));
    }

    @Override
    public BookDto findById(int id) {
        if (isBookIdExist(id)) {
            return convertBookEntityToBookDto(bookRepository.findById(id).get());
        }

        throw new NotFoundException(String.format("Book id %d not found", id));
    }

    @Override
    public PaginationResponse findByUserId(String keyword, int userId, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> bookPage;

        if (keyword == null) {
            bookPage = bookRepository.findByUserId(userId, paging);
        } else {
            bookPage = bookRepository.findByUserIdAndTitleContainsIgnoreCaseOrUserIdAndAuthorContainsIgnoreCase(
                    userId, keyword, userId, keyword, paging);
        }

        return new PaginationResponse(bookPage.getTotalElements(),
                convertAllBookEntityToBookDto(bookPage.getContent()));
    }

    @Override
    public PaginationResponse findByKeyword(String keyword, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> bookPage = bookRepository
                .findByTitleContainsIgnoreCaseOrAuthorContainsIgnoreCase(keyword, keyword, paging);

        return new PaginationResponse(bookPage.getTotalElements(),
                convertAllBookEntityToBookDto(bookPage.getContent()));
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

        if (bookEntity.getUser().getRole().getName().equals("ROLE_ADMIN")) {
            bookEntity.setEnabled(true);
        }

        bookEntity = bookRepository.save(bookEntity);

        return convertBookEntityToBookDto(bookEntity);
    }

    @Override
    public BookDto updateBook(int bookId, int userId, BookDto bookDto) {
        Book bookEntity = convertBookDtoToBookEntity(bookDto);

        if (!isBookIdExist(bookId)) {
            throw new NotFoundException(String.format("Book id %d not found", bookId));
        }

        if (isBookExist(bookEntity, bookId)) {
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

    private boolean isBookExist(Book book, int userId) {
        List<Book> bookListGetByTitle = bookRepository.findByTitle(book.getTitle());
        List<Book> bookListGetByAuthor = bookRepository.findByAuthor(book.getAuthor());
        if (!bookListGetByTitle.isEmpty() && !bookListGetByAuthor.isEmpty()) {
            return bookListGetByTitle.get(0).getId() != userId;
        }

        return false;
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
