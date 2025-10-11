package com.example.demo.repository;

import com.example.demo.models.entities.Payout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoutRepository extends JpaRepository<Payout, Long> {

  Payout findByReference(String transactionReference);
}
