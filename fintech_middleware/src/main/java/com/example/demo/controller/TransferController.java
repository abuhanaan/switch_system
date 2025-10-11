package com.example.demo.controller;

import com.example.demo.models.dto.request.TransferDto;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.entities.User;
import com.example.demo.service.TransferService;
import com.example.demo.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

  private final TransferService transferService;
  private final AuthUtil authUtil;

  @GetMapping(path = "banks", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> getAllBanks() {
    return ResponseEntity.ok(transferService.getAllBanks());
  }

  @PostMapping(path = "payout", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse> makeTransfer(@Valid @RequestBody TransferDto transferDto,
      BindingResult bindingResult, HttpServletRequest httpRequest) {
    User user = authUtil.getUserFromHttpRequest(httpRequest);
    ApiResponse response = transferService.processTransfer(transferDto, user);
    return ResponseEntity.ok(response);
  }
}
