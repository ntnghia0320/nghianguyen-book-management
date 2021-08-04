package com.ntnghia.bookmanagement.service;

import com.ntnghia.bookmanagement.payload.request.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto findById(int id);

    List<UserDto> findByKeyword(String keyword);

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(int id, UserDto user);

    UserDto setRoleUser(int userId, int roleId);

    void deleteUser(int id);
}
