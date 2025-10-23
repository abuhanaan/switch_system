package com.example.demo.service;

import com.example.demo.models.dto.request.IdentityValidationRequest;
import com.example.demo.models.dto.request.OnboardingRequest;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.entities.User;

public interface OnboardingService {

  ApiResponse onboardUser(OnboardingRequest request);

  ApiResponse validateIdentity(User user, IdentityValidationRequest request);
}
