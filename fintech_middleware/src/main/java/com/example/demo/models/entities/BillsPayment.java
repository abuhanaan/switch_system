package com.example.demo.models.entities;

import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.constants.VasCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "bill_payments")
public class BillsPayment extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
  private Account account;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private BillProduct product;

  @Enumerated(EnumType.STRING)
  @Column(name = "biller")
  private BillerEnum biller;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private VasCategory category;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "beneficiary", nullable = false)
  private String beneficiary; // e.g., phone number, meter number, etc.

  @Column(nullable = false, unique = true)
  private String transactionReference;

  @Column(name = "narration", length = 100)
  private String narration;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TransactionStatus status; // e.g., SUCCESS, FAILED, PENDING
}
