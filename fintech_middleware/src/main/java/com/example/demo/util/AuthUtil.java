package com.example.demo.util;

import com.example.demo.models.entities.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

  private final JwtService jwtService;
  private final UserService userService;

  public User getUserFromHttpRequest(HttpServletRequest httpRequest) {
    String authHeader = httpRequest.getHeader("Authorization");
    User user = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      String email = jwtService.extractEmail(token);
      user = userService.findUserByEmail(
          email); // or userRepository.findByEmail(email).orElse(null);
    }
    return user;
  }
}
