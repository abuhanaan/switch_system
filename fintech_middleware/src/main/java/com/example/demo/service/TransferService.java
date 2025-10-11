package com.example.demo.service;

import com.example.demo.models.dto.request.TransferDto;
import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.entities.User;
import jakarta.validation.Valid;

public interface TransferService {

  ApiResponse getAllBanks();

  ApiResponse processTransfer(@Valid TransferDto transferDto, User user);
}
