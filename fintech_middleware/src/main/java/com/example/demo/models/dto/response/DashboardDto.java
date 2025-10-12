package com.example.demo.models.dto.response;

import com.example.demo.models.constants.AccountType;
import com.example.demo.models.entities.Account;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class DashboardDto {
  private String firstName;
  private String lastName;
  private String email;
  private String accountNumber;
  private AccountType accountType;
  private BigDecimal accountBalance;
  private RecentTransactions recentTransactions;

  public static DashboardDto buildFromAccount(Account account){
    DashboardDto dto = new DashboardDto();
    dto.setFirstName(account.getCustomer().getFirstName());
    dto.setLastName(account.getCustomer().getLastName());
    dto.setEmail(account.getCustomer().getUser().getEmail());
    dto.setAccountNumber(account.getAccountNumber());
    dto.setAccountType(account.getAccountType());
    dto.setAccountBalance(account.getBalance());
    return dto;
  }
}
