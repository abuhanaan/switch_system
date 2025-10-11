package com.example.demo.util;

import com.example.demo.config.EncryptionConfig;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PinCryptoUtil {

  private final EncryptionConfig encryptionConfig;

  public String encrypt(String pin) throws Exception {
    SecretKeySpec key = new SecretKeySpec(encryptionConfig.getSecretKey().getBytes(),
        encryptionConfig.getAlgorithm());
    Cipher cipher = Cipher.getInstance(encryptionConfig.getAlgorithm());
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encrypted = cipher.doFinal(pin.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }

  public String decrypt(String encryptedPin) throws Exception {
    SecretKeySpec key = new SecretKeySpec(encryptionConfig.getSecretKey().getBytes(),
        encryptionConfig.getAlgorithm());
    Cipher cipher = Cipher.getInstance(encryptionConfig.getAlgorithm());
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decoded = Base64.getDecoder().decode(encryptedPin);
    byte[] decrypted = cipher.doFinal(decoded);
    return new String(decrypted);
  }
}
