package com.example.demo.service.impl;

import com.example.demo.exceptions.ProcessingException;
import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.constants.VasCategory;
import com.example.demo.models.dto.request.BillsPaymentRequest;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.BillProductDto;
import com.example.demo.models.dto.response.BillsPaymentResponse;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.Account;
import com.example.demo.models.entities.BillProduct;
import com.example.demo.models.entities.BillsPayment;
import com.example.demo.models.entities.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BillProductRepository;
import com.example.demo.repository.BillsRepository;
import com.example.demo.service.BillsPaymentService;
import com.example.demo.validator.ValidationUtil;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BillsPaymentServiceImpl implements BillsPaymentService {

  private final BillProductRepository billProductRepository;
  private final BillsRepository billsRepository;
  private final AccountRepository accountRepository;
  private final ValidationUtil validationUtil;

  @Override
  public ApiResponse getAllBillsCategory() {
//    return SuccessResponse.buildSuccess("Bills Categories Fetched Successfully!!", BillsCategories.getCategories());
    return SuccessResponse.buildSuccess("Bills Categories Fetched Successfully!!",
        Arrays.asList("DATA", "AIRTIME", "ELECTRICITY", "CABLE"));
  }

  @Override
  public ApiResponse getAllProductsByCategory(String category) {
    validationUtil.validateCategory(category);
    List<BillProduct> products = billProductRepository.findAllByCategory(
        VasCategory.valueOf(category.toUpperCase()));
    return SuccessResponse.buildSuccess(category + " Bills Products Fetched Successfully!!",
        BillProductDto.fromEntities(products));
  }

  @Transactional
  @Override
  public ApiResponse processPayment(BillsPaymentRequest request, User user) {
    validationUtil.validateCustomer(user.getCustomer());
    Account account = user.getCustomer().getAccount();
    BillProduct product =
        request.getProductId() != null ? getBillProduct(request.getProductId()) : null;
    validateRequest(request, account, product);
    BillsPayment bill = buildBill(account, request, product);

    // send request to third party provider for beneficiary top up
    // handle error from the api call to third party and send response to client accordingly
    bill.setBiller(product != null ? product.getBiller()
        : BillerEnum.valueOf(request.getBiller().toUpperCase()));
    bill.setCategory(product != null ? product.getCategory()
        : VasCategory.valueOf(request.getCategory().toUpperCase()));
    billsRepository.save(bill);
    setAccountBalance(account, product, request.getAmount());
    accountRepository.save(account);
    return SuccessResponse.buildSuccess("Bills Processed Successfully",
        BillsPaymentResponse.fromEntity(bill));
  }

  private void setAccountBalance(Account account, BillProduct product, BigDecimal requestAmount) {
    BigDecimal tranxAmount =
        (product != null && product.getAmount() != null) ? product.getAmount() : requestAmount;
    System.out.println("Account Balance: " + account.getBalance() + " trnxAmt: " + tranxAmount);
    if (tranxAmount != null) {
      account.setBalance(account.getBalance().subtract(tranxAmount));
    } else {
      throw new ProcessingException("Transaction Amount is Missing");
    }
  }

  private BillsPayment buildBill(Account account, BillsPaymentRequest request,
      BillProduct product) {
    return BillsPayment.builder()
        .account(account)
        .amount(request.getAmount())
        .beneficiary(request.getBeneficiary())
        .transactionReference(request.getTransactionReference())
        .narration(request.getNarration())
        .product(product)
        .biller(product != null ? product.getBiller()
            : BillerEnum.valueOf(request.getBiller().toUpperCase()))
        .category(product != null ? product.getCategory()
            : VasCategory.valueOf(request.getCategory().toUpperCase()))
        .amount(product != null ? product.getAmount() : request.getAmount())
        .transactionReference(request.getTransactionReference())
        .status(TransactionStatus.SUCCESS)
        .build();
  }

  private BillProduct getBillProduct(Long productId) {
    return billProductRepository.findOneById(productId);
  }

  private void validateRequest(BillsPaymentRequest request, Account account, BillProduct product) {
    validationUtil.validateDuplicateTransaction(request.getTransactionReference());
    validationUtil.validateTransactionPin(request.getTransactionPin(), account);
    validationUtil.validateFields(product != null ? product.getAmount() :
        request.getAmount(), "Amount cannot be null");
    if (request.getProductId() == null) {
      validationUtil.validateCategory((request.getCategory()));
      validationUtil.validateBiller(request.getBiller());
    }
    validationUtil.validateAmountIsSufficient(product != null ? product.getAmount() :
        request.getAmount(), account);
  }
}
