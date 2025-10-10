package com.example.demo.service;

import com.example.demo.models.dto.request.OnboardingRequest;
import com.example.demo.models.dto.response.ApiResponse;

public interface OnboardingService {

  ApiResponse onboardUser(OnboardingRequest request);
}
