package com.example.demo.models.dto.response;

import com.example.demo.models.entities.Account;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnboardingResponse {

  private String accountNumber;
  private BigDecimal balance;

  public static OnboardingResponse fromAccount(Account account){
    return OnboardingResponse.builder()
        .accountNumber(account.getAccountNumber()).balance(account.getBalance()).build();
  }
}
