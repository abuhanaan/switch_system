package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class TokenConfig {

  private Long expirationMs;
  private String secretKey;
}
