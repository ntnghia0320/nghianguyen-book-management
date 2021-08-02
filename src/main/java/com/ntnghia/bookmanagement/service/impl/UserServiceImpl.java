package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.User;
import com.ntnghia.bookmanagement.exception.BadRequestException;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.repository.RoleRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        if (isIdExist(id)) return userRepository.findById(id).get();

        throw new NotFoundException(String.format("User id %d not found", id));
    }

    @Override
    public List<User> findByKeyword(String keyword) {
        return userRepository.findByFirstNameContainsOrLastNameContainsOrEmailContains(keyword, keyword, keyword);
    }

    @Override
    public User saveUser(User user) {
        if (isEmailExist(user.getEmail())) {
            throw new BadRequestException("This email address is already being used");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER"));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        if (isIdExist(id)) {
            if (isUserNotChange(id, user)) {
                throw new BadRequestException("User not change");
            } else if (isEmailExist(user.getEmail())) {
                throw new BadRequestException("Email user duplicate");
            } else {
                user.setId(id);

                return userRepository.save(user);
            }
        }

        throw new NotFoundException(String.format("User id %d not found", id));
    }

    @Override
    public User setRoleUser(int id, int roleId) {
        if (isIdExist(id)) {
            User userOld = userRepository.findById(id).get();

            userOld.setId(id);
            userOld.setRole(roleRepository.findById(roleId).get());

            return userRepository.save(userOld);
        }

        throw new NotFoundException(String.format("User id %d not found", id));
    }

    @Override
    public void deleteUser(int id) {
        if (isIdExist(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("User id %d not found", id));
        }
    }

    private boolean isIdExist(int id) {
        return userRepository.existsById(id);
    }

    private boolean isUserNotChange(int userId, User userNew) {
        User userOld = userRepository.findById(userId).get();

        return userOld.getEmail().equals(userNew.getEmail())
                && userOld.getFirstName().equals(userNew.getFirstName())
                && userOld.getLastName().equals(userNew.getLastName())
                && userOld.getPassword().equals(userNew.getPassword());
    }

    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
