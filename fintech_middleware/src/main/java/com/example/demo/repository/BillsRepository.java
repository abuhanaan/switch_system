package com.example.demo.repository;

import com.example.demo.models.entities.BillsPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepository extends JpaRepository<BillsPayment, Long> {

  BillsPayment findByTransactionReference(String transactionReference);
}
