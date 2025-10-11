package com.example.demo.controller;

import com.example.demo.models.dto.request.OnboardingRequest;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.service.OnboardingService;
import com.example.demo.validator.InputValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

  private final OnboardingService onboardingService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> onboard(@Valid @RequestBody OnboardingRequest request,
      BindingResult bindingResult) {
    InputValidator.validate(bindingResult);
    ApiResponse response = onboardingService.onboardUser(request);
    return ResponseEntity.ok(response);
  }
}
