package com.example.demo.models.dto.response;

import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.entities.Payout;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferResponseDto {

  private String message;
  private String reference;
  //  private String senderAccountNumber;
//  private String senderBankCode;
//  private String senderName;
  private String beneficiaryAccountNumber;
  private String beneficiaryBankCode;
  private String beneficiaryName;
  private BigDecimal amount;
  private String narration;
  private TransactionStatus transactionStatus; // e.g., SUCCESS, FAILED, PENDING
  private BigDecimal accountBalance;

  public static TransferResponseDto fromEntity(Payout payout, BigDecimal accountBalance) {
    return TransferResponseDto.builder()
        .message("Transfer processed")
        .reference(payout.getReference())
        .beneficiaryAccountNumber(payout.getBeneficiaryAccountNumber())
        .beneficiaryBankCode(payout.getBeneficiaryBank() != null ?
            payout.getBeneficiaryBank().getCode() : null)
        .beneficiaryName(payout.getBeneficiaryName())
        .amount(payout.getAmount())
        .narration(payout.getNarration())
        .transactionStatus(payout.getStatus())
        .accountBalance(accountBalance)
        .build();
  }

  public static List<TransferResponseDto> fromEntities(List<Payout> entities,
      BigDecimal accountBalance) {
    return entities.stream().map(entity -> fromEntity(entity, accountBalance)).toList();
  }
}
