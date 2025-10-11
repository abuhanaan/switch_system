package com.example.demo.service.impl;

import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.User;

public interface AuthenticationService {

  User register(User user);

  SuccessResponse login(String email, String password);
}
