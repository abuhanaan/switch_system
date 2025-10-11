package com.example.demo.models.dto.request;

import com.example.demo.models.constants.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OnboardingRequest {

  // User fields
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password is required")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=]{8,}$",
      message = "Password must be at least 8 characters, contain letters and numbers"
  )
  private String password;

  @NotBlank(message = "First name is required")
  @Pattern(
      regexp = "^[A-Za-z\\-']{2,}$",
      message = "First name must contain only letters, hyphens, or apostrophes and be at least 2 characters"
  )
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Pattern(
      regexp = "^[A-Za-z\\-']{2,}$",
      message = "Last name must contain only letters, hyphens, or apostrophes and be at least 2 characters"
  )
  private String lastName;

  @NotBlank(message = "BVN is required")
  @Pattern(regexp = "^\\d{11}$", message = "BVN must be exactly 11 digits")
  private String bvn;

  @NotBlank(message = "NIN is required")
  @Pattern(regexp = "^\\d{11}$", message = "NIN must be exactly 11 digits")
  private String nin;

  @NotBlank(message = "Address is required")
  private String address;

  @NotNull(message = "Account type is required, can only be SAVINGS or CURRENT")
  private AccountType accountType; // e.g., "SAVINGS", "CURRENT"

  @NotNull(message = "Initial deposit is required")
  @DecimalMin(value = "2000.00", message = "Initial deposit must be at least 2000")
  private BigDecimal initialDeposit;

  @NotBlank(message = "Transaction PIN is required")
  @Pattern(
      regexp = "^(?!0123|1234|2345|3456|4567|5678|6789|7890|0987|9876|8765|7654|6543|5432|4321|3210)\\d{4}$",
      message = "Transaction PIN must be a 4-digit number and not sequential (e.g., 1234, 5678)"
  )
  private String transactionPin;
}
