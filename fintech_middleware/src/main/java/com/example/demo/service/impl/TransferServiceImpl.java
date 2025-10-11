package com.example.demo.service.impl;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.constants.PaymentType;
import com.example.demo.models.constants.TransactionStatus;
import com.example.demo.models.dto.request.TransferDto;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.BankDto;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.dto.response.TransferResponseDto;
import com.example.demo.models.entities.Account;
import com.example.demo.models.entities.Bank;
import com.example.demo.models.entities.Payin;
import com.example.demo.models.entities.Payout;
import com.example.demo.models.entities.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.PayinRepository;
import com.example.demo.repository.PayoutRepository;
import com.example.demo.service.TransferService;
import com.example.demo.validator.ValidationUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

  private final BankRepository bankRepository;
  private final AccountRepository accountRepository;
  private final ValidationUtil validationUtil;
  private final PayoutRepository payoutRepository;
  private final PayinRepository payinRepository;

  @Override
  public ApiResponse getAllBanks() {
    List<Bank> banks = bankRepository.findAll();
    return SuccessResponse.buildSuccess("Banks Fetched Successfully!!",
        BankDto.fromEntities(banks));
  }

  @Transactional
  @Override
  public ApiResponse processTransfer(TransferDto request, User user) {
    Account account = user.getCustomer().getAccount();
    validateRequest(request, account);
    Account beneficiary = accountRepository.findByAccountNumber(
        request.getBeneficiaryAccountNumber());
    Payout payout = null;
    if (beneficiary != null) {
      payout = processInternalTransfer(request, account, beneficiary);
    } else {
      payout = processOutboundTransfer(request, account);
    }
    return SuccessResponse.buildSuccess("Transfer Request Processed Successfully",
        TransferResponseDto.fromEntity(payout, account.getBalance()));
  }

  private Payout processInternalTransfer(TransferDto request, Account account,
      Account beneficiary) {
    beneficiary.setBalance(beneficiary.getBalance().add(request.getAmount()));
    account.setBalance(account.getBalance().subtract(request.getAmount()));
    accountRepository.save(beneficiary);
    accountRepository.save(account);
    Payout payout = setCommonPayoutProperties(request, account);
    setInternalBeneficiary(payout, beneficiary);
    payoutRepository.save(payout);
    Payin payin = buildPayin(request, beneficiary, account);
    payinRepository.save(payin);
    return payout;
  }

  private void setInternalBeneficiary(Payout payout, Account beneficiary) {
    payout.setType(PaymentType.INTERNAL);
    payout.setBeneficiary(beneficiary);
    payout.setBeneficiaryName(
        beneficiary.getCustomer().getFirstName() + " " + beneficiary.getCustomer().getLastName());
    payout.setBeneficiaryAccountNumber(beneficiary.getAccountNumber());
  }

  private Payout setCommonPayoutProperties(TransferDto request, Account account) {
    return Payout.builder()
        .reference(request.getReference())
        .account(account)
        .amount(request.getAmount())
        .narration(request.getNarration())
        .status(TransactionStatus.SUCCESS)
        .build();
  }

  private Payin buildPayin(TransferDto request, Account beneficiary, Account account) {
    return Payin.builder()
        .type(PaymentType.INTERNAL)
        .reference(request.getReference())
        .account(beneficiary)
        .sender(account)
        .senderAccountNumber(account.getAccountNumber())
        .senderName(
            account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName())
        .amount(request.getAmount())
        .narration(request.getNarration())
        .status(TransactionStatus.SUCCESS)
        .build();
  }

  private void validateRequest(TransferDto request, Account account) {
    ensureUserIsNotTransferingToThemselves(request.getBeneficiaryAccountNumber(), account);
    validationUtil.validateFields(request.getTransactionPin(), "TransactionPin is required");
    validationUtil.validateDuplicateTransaction(request.getReference(), true);
    validationUtil.validateAmountIsSufficient(request.getAmount(), account);
    validationUtil.validateTransactionPin(request.getTransactionPin(), account);
  }

  private void ensureUserIsNotTransferingToThemselves(String beneficiaryAccountNumber,
      Account account) {
    if (beneficiaryAccountNumber.equals(account.getAccountNumber())){
      throw new BadRequestException(beneficiaryAccountNumber + " is your account number, "
          + "you can only transfer to other account numbers");
    }
  }

  private Payout processOutboundTransfer(TransferDto request, Account account) {
    validationUtil.validateFields(request.getBeneficiaryBankCode(),
        "Beneficiary Bank code is required");
    // send Request to third party for outbound transfer
    // handle error from api call to third party and send response to client accordingly
    // if request to third party succeeds then we proceed with the business logic
    account.setBalance(account.getBalance().subtract(request.getAmount()));
    Payout payout = setCommonPayoutProperties(request, account);
    setOutBoundBeneficiary(payout, request);
    payoutRepository.save(payout);
    return payout;
  }

  private void setOutBoundBeneficiary(Payout payout, TransferDto request) {
    payout.setType(PaymentType.OUTBOUND);
    payout.setBeneficiaryName(request.getBeneficiaryName());
    payout.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
  }
}
