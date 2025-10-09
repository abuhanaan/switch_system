package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

  UserDetails loadUserByUsername(String email);
}
