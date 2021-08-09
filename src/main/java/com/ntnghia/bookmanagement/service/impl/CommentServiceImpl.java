package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.Comment;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.payload.request.BookDto;
import com.ntnghia.bookmanagement.payload.request.CommentDto;
import com.ntnghia.bookmanagement.payload.request.UserDto;
import com.ntnghia.bookmanagement.repository.BookRepository;
import com.ntnghia.bookmanagement.repository.CommentRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public CommentDto findById(int id) {
        if (isCommentIdExist(id)) {
            return convertCommentEntityToCommentDto(commentRepository.findById(id).get());
        }

        throw new NotFoundException(String.format("Comment id %d is not found", id));
    }

    @Override
    public List<CommentDto> findByBookId(int bookId) {
        if (isBookIdExist(bookId)) {
            return convertAllCommentEntityToCommentDto(commentRepository.findByBookId(bookId));
        }

        throw new NotFoundException(String.format("Book id %d is not found", bookId));
    }

    @Override
    public CommentDto saveComment(int userId, int bookId, CommentDto commentDto) {
        Comment commentEntity = convertCommentDtoToCommentEntity(commentDto);
        if (!isUserIdExist(userId)) {
            throw new NotFoundException(String.format("User id %d is not found", userId));
        } else if (!isBookIdExist(bookId)) {
            throw new NotFoundException(String.format("Book id %d is not found", bookId));
        }

        commentEntity.setUser(userRepository.findById(userId).get());
        commentEntity.setBook(bookRepository.findById(bookId).get());

        commentEntity = commentRepository.save(commentEntity);

        return convertCommentEntityToCommentDto(commentEntity);
    }

    @Override
    public CommentDto updateComment(int commentId, int userId, int bookId, CommentDto commentDto) {
        Comment commentEntity = convertCommentDtoToCommentEntity(commentDto);
        if (isCommentIdExist(commentId)) {
            commentEntity.setId(commentId);
            commentEntity.setUser(userRepository.findById(userId).get());
            commentEntity.setBook(bookRepository.findById(bookId).get());

            return convertCommentEntityToCommentDto(commentRepository.save(commentEntity));
        }

        throw new NotFoundException(String.format("Comment id %d is not found", commentId));
    }

    @Override
    public void deleteComment(int id) {
        if (!isCommentIdExist(id)) {
            throw new NotFoundException(String.format("Comment id %d is not found", id));
        }

        commentRepository.deleteById(id);
    }

    private boolean isCommentIdExist(int id) {
        return commentRepository.existsById(id);
    }

    private boolean isUserIdExist(int id) {
        return userRepository.existsById(id);
    }

    private boolean isBookIdExist(int id) {
        return bookRepository.existsById(id);
    }

    private CommentDto convertCommentEntityToCommentDto(Comment commentEntity) {
        UserDto userDto = modelMapper.map(commentEntity.getUser(), UserDto.class);
        BookDto bookDto = modelMapper.map(commentEntity.getBook(), BookDto.class);

        CommentDto commentDto = modelMapper.map(commentEntity, CommentDto.class);
        bookDto.setUser(userDto);
        commentDto.setBook(bookDto);

        return commentDto;
    }

    private Comment convertCommentDtoToCommentEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    private List<CommentDto> convertAllCommentEntityToCommentDto(List<Comment> comments) {
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }
}
