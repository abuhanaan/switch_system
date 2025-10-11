package com.example.demo.models.dto.response;

import com.example.demo.models.entities.Bank;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankDto {

  private Long id;
  private String name;
  private String code;
  private String country;
  private String currency;
  private Boolean active;

  public static List<BankDto> fromEntities(List<Bank> entities) {
    return entities.stream().map(e -> {
      return BankDto.builder()
          .id(e.getId())
          .name(e.getName())
          .code(e.getCode())
          .country(e.getCountry())
          .currency(e.getCurrency())
          .active(e.getActive())
          .build();
    }).toList();
  }
}
