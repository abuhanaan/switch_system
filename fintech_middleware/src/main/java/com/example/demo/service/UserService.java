package com.example.demo.service;

import com.example.demo.models.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

  UserDetails loadUserByUsername(String email);

  User findUserByEmail(String email);
}
