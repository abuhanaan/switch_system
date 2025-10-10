package com.example.demo.service;

import com.example.demo.models.dto.request.BillsPaymentRequest;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.dto.response.BillsPaymentResponse;
import com.example.demo.models.entities.User;
import jakarta.validation.Valid;

public interface BillsPaymentService {

  ApiResponse getAllBillsCategory();

  ApiResponse getAllProductsByCategory(String category);

  ApiResponse processPayment(BillsPaymentRequest request, User user);
}
