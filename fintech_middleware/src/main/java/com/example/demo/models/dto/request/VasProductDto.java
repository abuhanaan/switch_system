package com.example.demo.models.dto.request;

import java.math.BigDecimal;

public class VasProductDto {

  private Long id;
  private String name;
  private String description;
  private String category;    // e.g., "Telecom", "Utility"
  private String biller;      // e.g., "MTN", "9mobile"
  private BigDecimal minAmount;
  private boolean active;
}
