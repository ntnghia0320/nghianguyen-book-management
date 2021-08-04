package com.ntnghia.bookmanagement.service.impl;

import com.ntnghia.bookmanagement.entity.User;
import com.ntnghia.bookmanagement.exception.BadRequestException;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.payload.request.RoleDto;
import com.ntnghia.bookmanagement.payload.request.UserDto;
import com.ntnghia.bookmanagement.repository.RoleRepository;
import com.ntnghia.bookmanagement.repository.UserRepository;
import com.ntnghia.bookmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<UserDto> getAll() {
        return convertAllUserEntityToUserDto(userRepository.findAll());
    }

    @Override
    public UserDto findById(int id) {
        User userEntity = userRepository.findById(id).get();
        if (isIdExist(id)) return convertUserEntityToUserDto(userEntity);

        throw new NotFoundException(String.format("User id %d not found", id));
    }

    @Override
    public List<UserDto> findByKeyword(String keyword) {
        return convertAllUserEntityToUserDto(
                userRepository.findByFirstNameContainsOrLastNameContainsOrEmailContains(keyword, keyword, keyword)
        );
    }

    @Override
    public UserDto saveUser(UserDto userDto) {

        User userEntity = convertUserDtoToUserEntity(userDto);

        if (isEmailExist(userEntity.getEmail())) {
            throw new BadRequestException("This email address is already being used");
        }

        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity.setRole(roleRepository.findByName("ROLE_USER"));

        userEntity = userRepository.save(userEntity);

        return convertUserEntityToUserDto(userEntity);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User userEntity = convertUserDtoToUserEntity(userDto);

        if (isIdExist(id)) {
            if (isUserNotChange(id, userEntity)) {
                throw new BadRequestException("User not change");
            } else if (isEmailExist(userEntity.getEmail())) {
                throw new BadRequestException("This email address is already being used");
            } else {
                userEntity.setId(id);
                userEntity = userRepository.save(userEntity);

                return convertUserEntityToUserDto(userEntity);
            }
        }

        throw new NotFoundException(String.format("User id %d not found", id));
    }

    @Override
    public UserDto setRoleUser(int userId, int roleId) {
        if (isIdExist(userId)) {
            User userOld = userRepository.findById(userId).get();

            userOld.setId(userId);
            userOld.setRole(roleRepository.findById(roleId).get());
            User userEntity = userRepository.save(userOld);

            return convertUserEntityToUserDto(userEntity);
        }

        throw new NotFoundException(String.format("User id %d not found", userId));
    }

    @Override
    public void deleteUser(int id) {
        if (!isIdExist(id)) {
            throw new NotFoundException(String.format("User id %d not found", id));
        }
        userRepository.deleteById(id);
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

    private UserDto convertUserEntityToUserDto(User userEntity) {
        RoleDto roleDto = modelMapper.map(userEntity.getRole(), RoleDto.class);

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        userDto.setRole(roleDto);

        return userDto;
    }

    private User convertUserDtoToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private List<UserDto> convertAllUserEntityToUserDto(List<User> users) {
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
