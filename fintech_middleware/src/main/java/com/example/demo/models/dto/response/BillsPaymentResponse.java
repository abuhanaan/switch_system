package com.example.demo.models.dto.response;

import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.constants.VasCategory;
import com.example.demo.models.dto.request.BillsPaymentRequest;
import com.example.demo.models.entities.BillsPayment;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillsPaymentResponse {

  private String message;
  private String transactionReference;
  private BigDecimal amount;
  private String beneficiary;
  private BillerEnum billerName;
  private VasCategory billsCategory;
  private String productName;
  private String description;
  private TransactionStatus paymentStatus; // e.g., SUCCESS, FAILED, PENDING

  public static BillsPaymentResponse fromEntity(BillsPayment entity) {
    return BillsPaymentResponse.builder()
        .message("Bills Processed Successfully")
        .transactionReference(entity.getTransactionReference())
        .amount(entity.getAmount())
        .beneficiary(entity.getBeneficiary())
        .billerName(entity.getBiller())
        .billsCategory(entity.getCategory())
        .productName(entity.getProduct() != null ? entity.getProduct().getName() : null)
        .description(entity.getProduct() != null ? entity.getProduct().getDescription() : null)
        .paymentStatus(entity.getStatus())
        .build();
  }

  public static List<BillsPaymentResponse> fromEntities(List<BillsPayment> entities) {
    return entities.stream().map(BillsPaymentResponse::fromEntity).toList();
  }
}
