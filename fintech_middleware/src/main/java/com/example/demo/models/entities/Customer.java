package com.example.demo.models.entities;

import com.example.demo.models.constants.OnboardingStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
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
@Table(name = "customers")
public class Customer extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "bvn", nullable = false)
  private String bvn;

  @Column(name = "nin", nullable = false)
  private String nin;

  @Column(name = "address")
  private String address;

  @Column(name = "onboarding_status")
  private OnboardingStatus onboardingStatus;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
  private Account account;
}
