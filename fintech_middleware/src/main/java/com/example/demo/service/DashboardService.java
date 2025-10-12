package com.example.demo.service;

import com.example.demo.models.dto.response.ApiResponse;
import com.example.demo.models.entities.User;

public interface DashboardService {

  ApiResponse getDashboard(User user);
}
