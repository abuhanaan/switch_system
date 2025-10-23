package com.example.demo.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IdentityValidationRequest {

  @NotBlank(message = "Please input the last 5 digit of your BVN")
  @Pattern(
      regexp = "^\\d{5}$",
      message = "Must be a 5-digit number"
  )
  private String lastFiveDigitOfBvn;

  @NotBlank(message = "Please input the last 5 digit of your NIN")
  @Pattern(
      regexp = "^\\d{5}$",
      message = "Must be a 5-digit number"
  )
  private String lastFiveDigitOfNin;
}
