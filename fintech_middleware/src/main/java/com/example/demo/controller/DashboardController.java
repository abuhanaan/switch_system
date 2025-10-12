package com.example.demo.controller;

import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.entities.User;
import com.example.demo.service.DashboardService;
import com.example.demo.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final AuthUtil authUtil;
  private final DashboardService dashboardService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> getDashboard(HttpServletRequest httpRequest) {
    User user = authUtil.getUserFromHttpRequest(httpRequest);
    return ResponseEntity.ok(dashboardService.getDashboard(user));
  }
}
