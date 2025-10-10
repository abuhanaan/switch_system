package com.example.demo.repository;

import com.example.demo.models.constants.VasCategory;
import com.example.demo.models.entities.BillProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct, Long> {

  List<BillProduct> findAllByCategory(VasCategory category);
  BillProduct findOneById(Long id);
}
