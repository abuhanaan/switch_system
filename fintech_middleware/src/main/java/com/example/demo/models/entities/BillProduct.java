package com.example.demo.models.entities;

import com.example.demo.models.constants.BillerEnum;
import com.example.demo.models.constants.VasCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill_products")
public class BillProduct extends BaseEntity{

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private VasCategory category;

  @Enumerated(EnumType.STRING)
  @Column(name = "biller")
  private BillerEnum biller;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column
  private String description;

  @Column(nullable = false)
  private boolean active = true;
}
