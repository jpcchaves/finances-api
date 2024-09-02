package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.Expense;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query(value = "SELECT * FROM expenses WHERE user_id = :userId", nativeQuery = true)
  Page<Expense> findAll(Long userId, Pageable pageable);

  @Query(value = "SELECT * FROM expenses WHERE user_id = :userId", nativeQuery = true)
  List<Expense> findAll(Long userId);
}
