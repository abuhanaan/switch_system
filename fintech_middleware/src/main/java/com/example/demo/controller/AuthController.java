package com.example.demo.controller;

import com.example.demo.models.dto.request.LoginRequest;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.User;
import com.example.demo.service.impl.AuthenticationService;
import com.example.demo.service.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/register")
  public User register(@RequestBody User user) {
    return authenticationService.register(user);
  }

  @PostMapping("/login")
  public SuccessResponse login(@RequestBody LoginRequest loginRequest) {
    return authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
  }
}
