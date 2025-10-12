package com.example.demo.models.dto.response;

import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.entities.Payout;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardPayoutDto {

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

  public static List<DashboardPayoutDto> fromEntities(List<Payout> payouts) {
    return payouts.stream().map(p -> {
      return DashboardPayoutDto.builder()
          .reference(p.getReference())
          .beneficiaryAccountNumber(p.getBeneficiaryAccountNumber())
          .beneficiaryBankCode(
              p.getBeneficiaryBank() != null ? p.getBeneficiaryBank().getCode() : null)
          .beneficiaryName(p.getBeneficiaryName())
          .amount(p.getAmount())
          .narration(p.getNarration())
          .transactionStatus(p.getStatus())
          .build();
    }).toList();
  }
}
