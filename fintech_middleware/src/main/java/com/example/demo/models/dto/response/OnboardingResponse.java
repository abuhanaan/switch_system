package com.example.demo.models.dto.response;

import com.example.demo.models.constants.AccountType;
import com.example.demo.models.entities.Account;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnboardingResponse {

  private String accountNumber;
  private BigDecimal balance;
  private AccountType accountType;

  public static OnboardingResponse fromAccount(Account account) {
    return OnboardingResponse.builder()
        .accountNumber(account.getAccountNumber())
        .balance(account.getBalance())
        .accountType(account.getAccountType())
        .build();
  }
}
