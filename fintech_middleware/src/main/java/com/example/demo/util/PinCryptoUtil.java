package com.example.demo.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PinCryptoUtil {
  private static final String ALGORITHM = "AES";
  private static final String SECRET_KEY = "1234567890123456"; // 16-char key for AES-128

  public static String encrypt(String pin) throws Exception {
    SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encrypted = cipher.doFinal(pin.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }

  public static String decrypt(String encryptedPin) throws Exception {
    SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decoded = Base64.getDecoder().decode(encryptedPin);
    byte[] decrypted = cipher.doFinal(decoded);
    return new String(decrypted);
  }
}
