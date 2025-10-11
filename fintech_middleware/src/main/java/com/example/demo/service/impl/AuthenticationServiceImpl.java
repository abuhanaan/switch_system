package com.example.demo.service.impl;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.dto.response.LoginResponse;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.User;
import com.example.demo.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setUserId(UUID.randomUUID().toString());
    return userRepository.save(user);
  }

  public SuccessResponse login(String email, String password) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password)
    );
    if (authentication.isAuthenticated()) {
      return SuccessResponse.buildSuccess("Login Successfully!",
          LoginResponse.fromToken(jwtService.generateToken(email)));
    }
    throw new BadRequestException("Invalid login credentials");
  }
}
