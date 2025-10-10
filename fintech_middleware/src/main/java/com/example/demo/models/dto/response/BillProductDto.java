package com.example.demo.models.dto.response;

import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.VasCategory;
import com.example.demo.models.entities.BillProduct;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillProductDto {

  private Long id;
  private String name;
  private VasCategory category;      // e.g., "DATA", "CABLE"
  private BillerEnum biller;        // e.g., "MTN", "DSTV"
  private BigDecimal amount;
  private String description;
  private boolean active;

  public static List<BillProductDto> fromEntities(List<BillProduct> entities) {
    return entities.stream().map(e -> {
      return BillProductDto.builder()
          .id(e.getId())
          .name(e.getName())
          .category(e.getCategory())
          .biller(e.getBiller())
          .amount(e.getAmount())
          .description(e.getDescription())
          .active(e.isActive())
          .build();
    }).toList();
  }
}
