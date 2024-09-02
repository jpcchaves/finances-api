package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.FinancialCategory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialCategoryRepository extends JpaRepository<FinancialCategory, Long> {

  @Query(value = "SELECT * FROM financial_categories WHERE user_id = :userId", nativeQuery = true)
  Page<FinancialCategory> findAll(Long userId, Pageable pageable);

  @Query(value = "SELECT * FROM financial_categories WHERE user_id = :userId", nativeQuery = true)
  List<FinancialCategory> findAll(Long userId);
}
