package com.example.demo.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

  private String accessToken;

  public static LoginResponse fromToken(String accessToken) {
    return LoginResponse.builder().accessToken(accessToken).build();
  }
}
