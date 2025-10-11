package com.example.demo.models.entities;

import com.example.demo.models.constants.PaymentType;
import com.example.demo.models.constants.TransactionStatus;
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
@Table(name = "payouts")
public class Payout extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private PaymentType type;

  @Column(name = "reference")
  private String reference;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
  private Account account; // sender

  @ManyToOne
  @JoinColumn(name = "beneficiary_account_id", referencedColumnName = "id")
  private Account beneficiary; // for inbound transfer

  @ManyToOne
  @JoinColumn(name = "beneficiary_bank_id", referencedColumnName = "id")
  private Bank beneficiaryBank; // for outBound transfer

  @Column(name = "beneficiary_acct_number")
  private String beneficiaryAccountNumber; // for outBound transfer

  @Column(name = "beneficiary_name")
  private String beneficiaryName; // for outBound transfer

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "narration")
  private String narration;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TransactionStatus status;
}
