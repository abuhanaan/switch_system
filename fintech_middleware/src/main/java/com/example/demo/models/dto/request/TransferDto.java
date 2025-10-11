package com.example.demo.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransferDto {

  @NotBlank(message = "beneficiary account number is required")
  private String beneficiaryAccountNumber; // Beneficiary account number
  private String beneficiaryBankCode;      // Beneficiary bank code (required for outbound transaction)
  private String beneficiaryName;          // Beneficiary name (required for outbound transaction)

  @NotNull(message = "Amount is required")
  private BigDecimal amount;               // Transfer amount

  private String narration;               // Transfer narration

  @NotBlank(message = "reference is required")
  private String reference;                // Transaction reference
}
