package com.example.demo.repository;

import com.example.demo.models.entities.Payin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayinRepository extends JpaRepository<Payin, Long> {

}
