package com.example.demo.validator;

import com.example.demo.exceptions.AuthorizationException;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ProcessingException;
import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.VasCategory;
import com.example.demo.models.entities.Account;
import com.example.demo.models.entities.BillsPayment;
import com.example.demo.models.entities.Customer;
import com.example.demo.models.entities.Payout;
import com.example.demo.repository.BillsRepository;
import com.example.demo.repository.PayoutRepository;
import com.example.demo.util.PinCryptoUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

  private final PinCryptoUtil pinCryptoUtil;
  private final BillsRepository billsRepository;
  private final PayoutRepository payoutRepository;

  public <T> void validateFields(T param, String message) {
    if (param == null) {
      throw new BadRequestException(message);
    }
  }

  public void validateCustomer(Customer customer) {
    if (!customer.isIdValidated()) {
      throw new AuthorizationException("Please Validate Your BVN and NIN");
    }
  }

  public void validateDuplicateTransaction(String transactionReference) {
    BillsPayment billsPayment = billsRepository.findByTransactionReference(transactionReference);
    if (billsPayment != null) {
      throw new ConflictException("Duplicate Transaction");
    }
  }

  public void validateDuplicateTransaction(String transactionReference, boolean isPayout) {
    if (isPayout) {
      Payout transfer = payoutRepository.findByReference(transactionReference);
      if (transfer != null) {
        throw new ConflictException("Duplicate Transaction");
      }
    }
  }

  public void validateTransactionPin(String transactionPin, Account account) {
    try {
      String pin = pinCryptoUtil.decrypt(account.getTransactionPin());
      if (!pin.equals(transactionPin)) {
        throw new BadRequestException("Transaction pin mismatch");
      }
    } catch (Exception e) {
      throw new ProcessingException(e.getMessage());
    }
  }

  public void validateAmountIsSufficient(BigDecimal amount, Account account) {
    if (account.getBalance().compareTo(amount) < 0) {
      throw new BadRequestException("Insufficient Balance");
    }
  }

  public void validateCategory(String category) {
    validateFields(category, "Please input category");
    boolean isValid = Arrays.stream(VasCategory.values()).anyMatch(
        c -> c.name().equalsIgnoreCase(category));
    if (!isValid) {
      throw new BadRequestException("Invalid Category, we currently support " + Arrays.toString(
          VasCategory.values()));
    }
  }

  public void validateBiller(String biller) {
    validateFields(biller, "please input biller");
    boolean isValid = Arrays.stream(BillerEnum.values()).anyMatch(
        c -> c.name().equalsIgnoreCase(biller));
    if (!isValid) {
      throw new BadRequestException("Invalid Category, we currently support " + Arrays.toString(
          BillerEnum.values()));
    }
  }

}
