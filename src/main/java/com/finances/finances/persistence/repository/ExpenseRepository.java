package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.Expense;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query(value = "SELECT * FROM expenses WHERE user_id = :userId", nativeQuery = true)
  Page<Expense> findAll(Long userId, Pageable pageable);

  @Query(value = "SELECT * FROM expenses WHERE user_id = :userId", nativeQuery = true)
  List<Expense> findAll(Long userId);

  @Query(
      value = "SELECT * FROM expenses WHERE user_id = :userId AND id = :expenseId",
      nativeQuery = true)
  Optional<Expense> findById(Long userId, Long expenseId);

  @Query(
      "SELECT c.name, SUM(e.amount) "
          + "FROM Expense e JOIN e.category c WHERE e.user.id = :userId "
          + "AND e.dueDate BETWEEN :startDate AND :endDate GROUP BY c.name")
  List<Object[]> findTotalAmountByAllCategories(
      @Param("userId") Long userId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query(
      "SELECT s.name, SUM(e.amount) "
          + "FROM Expense e JOIN e.supplier s WHERE e.user.id = :userId "
          + "AND e.dueDate BETWEEN :startDate AND :endDate GROUP BY s.name")
  List<Object[]> findTotalAmountByAllSuppliers(
      @Param("userId") Long userId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}
