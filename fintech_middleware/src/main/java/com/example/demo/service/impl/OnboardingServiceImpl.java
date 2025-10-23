package com.example.demo.service.impl;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ProcessingException;
import com.example.demo.models.constants.OnboardingStatus;
import com.example.demo.models.dto.request.IdentityValidationRequest;
import com.example.demo.models.dto.request.OnboardingRequest;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.OnboardingResponse;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.Account;
import com.example.demo.models.entities.Customer;
import com.example.demo.models.entities.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OnboardingService;
import com.example.demo.util.PinCryptoUtil;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;
  private final AccountRepository accountRepository;
  private final AuthenticationServiceImpl authService;
  private final PinCryptoUtil pinCryptoUtil;

  @Override
  public ApiResponse validateIdentity(User user, IdentityValidationRequest request) {
    Customer customer = user.getCustomer();
    if (customer.isIdValidated()) {
      return SuccessResponse.buildSuccess("Your Identity Is Previously Verified");
    }
    validateBvnAndNin(customer.getBvn(), customer.getNin(), request);
    customer.setIdValidated(true);
    customerRepository.save(customer);
    return SuccessResponse.buildSuccess("Identity Validated Successfully!!!");
  }

  @Transactional
  @Override
  public ApiResponse onboardUser(OnboardingRequest request) {
    validateRequest(request);
    User newUser = authService.register(
        User.builder().password(request.getPassword()).email(request.getEmail()).build());
    Customer newCustomer = createCustomer(request, newUser);
    Account account = createAccount(request, newCustomer);
    return SuccessResponse.buildSuccess("Account Created Successfully!!!",
        OnboardingResponse.fromAccount(account));
  }

  private void validateBvnAndNin(String bvn, String nin, IdentityValidationRequest request) {
    if (bvn == null || nin == null) {
      throw new ProcessingException("Missing BVN/NIN in Customer Profile");
    }
    if (!bvn.substring(bvn.length() - 5).equals(request.getLastFiveDigitOfBvn())) {
      throw new BadRequestException("Wrong Last Five Digit of BVN");
    }
    if (!nin.substring(nin.length() - 5).equals(request.getLastFiveDigitOfNin())) {
      throw new BadRequestException("Wrong Last Five Digit of NIN");
    }
  }

  private Account createAccount(OnboardingRequest request, Customer newCustomer) {
    try {
      Account account = Account.builder()
          .customer(newCustomer)
          .accountType(request.getAccountType())
          .balance(request.getInitialDeposit())
          .payouts(new ArrayList<>())
          .transactionPin(pinCryptoUtil.encrypt(request.getTransactionPin()))
          .build();
      accountRepository.save(account);
      String accountNumber = String.format("%010d", account.getId());
      account.setAccountNumber(accountNumber);
      account.setPayins(new ArrayList<>());
      accountRepository.save(account);
      newCustomer.setAccount(account);
      return account;
    } catch (Exception e) {
      throw new ProcessingException(e.getMessage());
    }
  }


  private Customer createCustomer(OnboardingRequest request, User newUser) {
    return customerRepository.save(Customer.builder()
        .user(newUser)
        .nin(request.getNin())
        .bvn(request.getBvn())
        .idValidated(false)
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .address(request.getAddress())
        .onboardingStatus(OnboardingStatus.PENDING)
        .build());
  }

  private void validateRequest(OnboardingRequest request) {
    confirmEmailUniqueness(request.getEmail());
    confirmNinUniqueness(request.getNin());
    confirmBvnUniqueness(request.getBvn());
  }


  private void confirmBvnUniqueness(String bvn) {
    if (customerRepository.findByBvn(bvn).isPresent()) {
      throw new ConflictException("A customer with the provided bvn already exists");
    }
  }

  private void confirmNinUniqueness(String nin) {
    if (customerRepository.findByNin(nin).isPresent()) {
      throw new ConflictException("A customer with the provided nin already exists");
    }
  }

  private void confirmEmailUniqueness(String email) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new ConflictException("A customer with the provided email already exists");
    }
  }
}
