package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.payload.request.UserDto;
import com.ntnghia.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping(value = "/search", params = "keyword")
    public List<UserDto> getByKeyword(@RequestParam("keyword") String keyword) {
        return userService.findByKeyword(keyword);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDto put(@PathVariable int id, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @PutMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto setRole(@PathVariable(value = "userId") int userId,
                           @PathVariable(value = "roleId") int roleId) {
        return userService.setRoleUser(userId, roleId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        userService.deleteUser(id);
    }
}