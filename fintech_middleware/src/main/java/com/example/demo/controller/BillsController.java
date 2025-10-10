package com.example.demo.controller;

import com.example.demo.models.dto.request.BillsPaymentRequest;
import com.example.demo.models.dto.request.VasProductDto;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.BillsPaymentResponse;
import com.example.demo.models.dto.response.SuccessResponse;
import com.example.demo.models.entities.User;
import com.example.demo.service.BillsPaymentService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.JwtService;
import com.example.demo.validator.InputValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillsController {

  private final BillsPaymentService billsPaymentService;
  private final JwtService jwtService;
  private final UserService userService;

//  @PostMapping
//  public VasProductDto createVasProduct(@Valid @RequestBody VasProductDto vasProductDto) {
//    return vasProductService.createVasProduct(vasProductDto);
//  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> getAllBillsCategory() {
    return ResponseEntity.ok(billsPaymentService.getAllBillsCategory());
  }

  @GetMapping(path = "/products/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable String category) {
    return ResponseEntity.ok(billsPaymentService.getAllProductsByCategory(category));
  }

  @PostMapping(path = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> payBill(@Valid @RequestBody BillsPaymentRequest request,
      BindingResult bindingResult, HttpServletRequest httpRequest) {
    InputValidator.validate(bindingResult);
    String authHeader = httpRequest.getHeader("Authorization");
    User user = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      String email = jwtService.extractEmail(token);
      user = userService.findUserByEmail(email); // or userRepository.findByEmail(email).orElse(null);
    }
    ApiResponse response = billsPaymentService.processPayment(request, user);
    return ResponseEntity.ok(response);
  }
}
