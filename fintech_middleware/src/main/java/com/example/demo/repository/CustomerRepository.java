package com.example.demo.repository;

import com.example.demo.models.entities.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByNin(String nin);

  Optional<Customer> findByBvn(String bvn);
}
