package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.FinancialCategory;
import java.util.List;
import java.util.Optional;
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

  @Query(
      value =
          "SELECT * FROM financial_categories WHERE user_id = :userId AND id = :financialCategoryId",
      nativeQuery = true)
  Optional<FinancialCategory> findById(Long userId, Long financialCategoryId);

  @Query(
      value =
          "SELECT * FROM financial_categories WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name ,'%')) AND user_id = :userId LIMIT 1",
      nativeQuery = true)
  Optional<FinancialCategory> findByName(Long userId, String name);

  @Query(
      value =
          "SELECT COUNT(*) > 0 FROM financial_categories WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')) AND user_id = :userId",
      nativeQuery = true)
  boolean existsByName(Long userId, String name);
}
