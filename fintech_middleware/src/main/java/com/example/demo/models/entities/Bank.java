package com.example.demo.models.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bank")
public class Bank extends BaseEntity {

  @Basic
  @Column(name = "name")
  private String name;

  @Basic
  @Column(name = "code")
  private String code;

  @Basic
  @Column(name = "country")
  private String country;

  @Basic
  @Column(name = "currency")
  private String currency;

  @Basic
  @Column(name = "active")
  private Boolean active;
}
