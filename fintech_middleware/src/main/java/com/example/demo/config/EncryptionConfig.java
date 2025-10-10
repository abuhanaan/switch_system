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
@ConfigurationProperties(prefix = "encryption")
public class EncryptionConfig {

  private String secretKey; // 16-char key for AES-128
  private String algorithm;
}
