package com.example.demo.service;

import com.example.demo.models.dto.request.OnboardingRequest;
import com.example.demo.models.dto.response.SuccessResponse;

public interface OnboardingService {

  SuccessResponse onboardUser(OnboardingRequest request);
}
