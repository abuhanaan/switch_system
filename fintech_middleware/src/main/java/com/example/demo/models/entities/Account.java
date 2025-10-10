package com.example.demo.models.entities;

import com.example.demo.models.constants.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  @Column(unique = true)
  private String accountNumber;

  @Column(name = "transaction_pin")
  private String transactionPin;

  @Column(nullable = false)
  private AccountType accountType; // e.g., SAVINGS, CURRENT

  @Column(nullable = false)
  private BigDecimal balance;
}
