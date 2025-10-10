package com.example.demo.service.impl;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws NotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(
            String.format("User with email: %s does not exist", email)));
  }

  @Override
  public User findUserByEmail(String email) throws NotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(
            String.format("User with email: %s does not exist", email)));
  }
}
