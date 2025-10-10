package com.example.demo.models.entities;

import com.example.demo.models.constants.AccountType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

  @OneToMany(mappedBy = "account")
  private List<Payout> payouts;

  @OneToMany(mappedBy = "account")
  private List<Payin> payins;

  @Column(unique = true)
  private String accountNumber;

  @Column(name = "transaction_pin")
  private String transactionPin;

  @Column(nullable = false)
  private AccountType accountType; // e.g., SAVINGS, CURRENT

  @Column(nullable = false)
  private BigDecimal balance;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<BillsPayment> bills;
}
