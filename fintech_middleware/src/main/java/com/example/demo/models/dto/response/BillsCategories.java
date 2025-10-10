package com.example.demo.models.dto.response;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillsCategories {
  private List<String> categories;

  public static BillsCategories getCategories() {
    return BillsCategories.builder()
        .categories(Arrays.asList("DATA", "AIRTIME", "ELECTRICITY", "CABLE"))
        .build();
  }
}
