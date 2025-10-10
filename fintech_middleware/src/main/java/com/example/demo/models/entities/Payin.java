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
import java.util.UUID;
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
public class Payin extends BaseEntity {

  @Column(name = "type")
  private PaymentType type;

  @Column(name = "reference")
  private UUID reference;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
  private Account account; // beneficiary

  @ManyToOne
  @JoinColumn(name = "sender_account_id", referencedColumnName = "id")
  private Account sender; // for inbound payin

  @ManyToOne
  @JoinColumn(name = "sender_bank_id", referencedColumnName = "id")
  private Bank senderBank; // for outBound payin

  @Column(name = "sender_acct_number")
  private String senderAccountNumber; // for outBound transfer

  @Column(name = "beneficiary_name")
  private String senderName; // for outBound transfer

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "narration")
  private String narration;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TransactionStatus status;
}
