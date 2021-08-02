package com.ntnghia.bookmanagement.security.service.impl;

import com.ntnghia.bookmanagement.entity.User;
import com.ntnghia.bookmanagement.exception.NotFoundException;
import com.ntnghia.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("User Not Found with email: " + email);

        return UserDetailsImpl.build(user);
    }
}
