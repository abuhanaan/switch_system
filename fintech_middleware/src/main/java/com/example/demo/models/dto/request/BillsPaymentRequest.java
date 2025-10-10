package com.example.demo.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BillsPaymentRequest {

  private Long productId;

  private BigDecimal amount;

  private String biller;

  private String category;

  @NotBlank(message = "Beneficiary is required")
  @Size(min = 4, max = 30, message = "Beneficiary must be between 4 and 30 characters")
  private String beneficiary; // e.g., phone number, meter number, decoder number etc.

  @NotBlank(message = "Transaction reference is required")
  private String transactionReference;

  @Size(max = 100, message = "Narration must not exceed 100 characters")
  private String narration;

  @NotBlank(message = "Transaction PIN is required")
  @Pattern(
      regexp = "^\\d{4}$",
      message = "Transaction PIN must be a 4-digit number"
  )
  private String transactionPin;
}
