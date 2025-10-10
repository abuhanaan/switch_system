package com.example.demo.service.impl;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ProcessingException;
import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.constants.VasCategory;
import com.example.demo.models.dto.request.BillsPaymentRequest;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.BillProductDto;
import com.example.demo.models.dto.response.BillsCategories;
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
import com.example.demo.util.PinCryptoUtil;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BillsPaymentServiceImpl implements BillsPaymentService {

  private final BillProductRepository billProductRepository;
  private final PinCryptoUtil pinCryptoUtil;
  private final BillsRepository billsRepository;
  private final AccountRepository accountRepository;

  @Override
  public ApiResponse getAllBillsCategory() {
//    return SuccessResponse.buildSuccess("Bills Categories Fetched Successfully!!", BillsCategories.getCategories());
    return SuccessResponse.buildSuccess("Bills Categories Fetched Successfully!!", Arrays.asList("DATA", "AIRTIME", "ELECTRICITY", "CABLE"));
  }

  @Override
  public ApiResponse getAllProductsByCategory(String category) {
    validateCategory(category);
    List<BillProduct> products = billProductRepository.findAllByCategory(VasCategory.valueOf(category.toUpperCase()));
    return SuccessResponse.buildSuccess(category + " Bills Products Fetched Successfully!!",
        BillProductDto.fromEntities(products));
  }

  @Transactional
  @Override
  public ApiResponse processPayment(BillsPaymentRequest request, User user) {
    Account account = user.getCustomer().getAccount();
    BillProduct product = request.getProductId() != null ? getBillProduct(request.getProductId()) : null;
    validateRequest(request, account, product);
    BillsPayment bill = buildBill(account, request, product);

    // send request to third party provider for beneficiary top up
    // handle error from the api call to third party and send response to client accordingly
    bill.setBiller(product != null ? product.getBiller() : BillerEnum.valueOf(request.getBiller().toUpperCase()));
    bill.setCategory(product != null ? product.getCategory() : VasCategory.valueOf(request.getCategory().toUpperCase()));
    billsRepository.save(bill);
    setAccountBalance(account, product, request.getAmount());
    accountRepository.save(account);
    return SuccessResponse.buildSuccess("Bills Processed Successfully", BillsPaymentResponse.fromEntity(bill, request));
  }

  private void setAccountBalance(Account account, BillProduct product, BigDecimal requestAmount) {
    BigDecimal tranxAmount = (product != null && product.getAmount() != null) ? product.getAmount() : requestAmount;
    System.out.println("Account Balance: " + account.getBalance() + " trnxAmt: " + tranxAmount);
    if (tranxAmount != null) {
      account.setBalance(account.getBalance().subtract(tranxAmount));
    } else {
      throw new ProcessingException("Transaction Amount is Missing");
    }
  }

  private BillsPayment buildBill(Account account, BillsPaymentRequest request, BillProduct product){
    return BillsPayment.builder()
        .account(account)
        .amount(request.getAmount())
        .beneficiary(request.getBeneficiary())
        .transactionReference(request.getTransactionReference())
        .narration(request.getNarration())
        .product(product)
        .amount(product != null ? product.getAmount() : request.getAmount())
        .transactionReference(request.getTransactionReference())
        .status(TransactionStatus.SUCCESS)
        .build();
  }

  private BillProduct getBillProduct(Long productId) {
    return billProductRepository.findOneById(productId);
  }

  private void validateRequest(BillsPaymentRequest request, Account account, BillProduct product) {
    validateDuplicateTransaction(request.getTransactionReference());
    validateTransactionPin(request.getTransactionPin(), account);
    if (request.getProductId() == null) {
      validateCategory(request.getCategory());
      validateBiller(request.getBiller());
    }
    validateAmountIsSufficient(product != null ? product.getAmount() : request.getAmount(), account);
  }

  private void validateDuplicateTransaction(String transactionReference) {
    BillsPayment billsPayment = billsRepository.findByTransactionReference(transactionReference);
    if (billsPayment != null){
      throw new ConflictException("Duplicate Transaction");
    }
  }

  private void validateTransactionPin(String transactionPin, Account account) {
    try {
      String pin = pinCryptoUtil.decrypt(account.getTransactionPin());
      if(!pin.equals(transactionPin)){
        throw new BadRequestException("Transaction pin mismatch");
      }
    } catch (Exception e){
      throw new ProcessingException(e.getMessage());
    }
  }

  private void validateAmountIsSufficient(BigDecimal amount, Account account) {
    if (account.getBalance().compareTo(amount) < 0){
      throw new BadRequestException("Insufficient Balance");
    }
  }

  private void validateCategory(String category) {
    boolean isValid = Arrays.stream(VasCategory.values()).anyMatch(
        c->c.name().equalsIgnoreCase(category));
    if(!isValid){
      throw new BadRequestException("Invalid Category, we currently support " + Arrays.toString(
          VasCategory.values()));
    }
  }

  private void validateBiller(String biller) {
    boolean isValid = Arrays.stream(BillerEnum.values()).anyMatch(
        c->c.name().equalsIgnoreCase(biller));
    if(!isValid){
      throw new BadRequestException("Invalid Category, we currently support " + Arrays.toString(
          BillerEnum.values()));
    }
  }
}
