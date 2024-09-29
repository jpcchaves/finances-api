package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.Expense;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseReportRepository extends JpaRepository<Expense, Long> {

  @Query(
      "SELECT c.name, SUM(e.amount) "
          + "FROM Expense e JOIN e.category c WHERE e.user.id = :userId "
          + "AND e.dueDate BETWEEN :startDate AND :endDate GROUP BY c.name")
  List<Object[]> findTotalAmountByAllCategories(
      @Param("userId") Long userId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query(
      "SELECT c.name, SUM(e.amount) "
          + "FROM Expense e JOIN e.category c WHERE e.user.id = :userId AND c.id = :categoryId "
          + "AND e.dueDate BETWEEN :startDate AND :endDate GROUP BY c.name")
  List<Object[]> findTotalAmountByCategory(
      @Param("userId") Long userId,
      @Param("categoryId") Long categoryId,
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

  @Query(
      "SELECT s.name, SUM(e.amount) "
          + "FROM Expense e JOIN e.supplier s WHERE e.user.id = :userId AND s.id = :supplierId "
          + "AND e.dueDate BETWEEN :startDate AND :endDate GROUP BY s.name")
  List<Object[]> findTotalAmountBySupplier(
      @Param("userId") Long userId,
      @Param("supplierId") Long supplierId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query(
      value =
          "SELECT EXTRACT(MONTH FROM due_date) - 1, SUM(e.amount) "
              + "FROM expenses e WHERE e.user_id = :userId "
              + "AND EXTRACT(YEAR FROM e.due_date) = EXTRACT(YEAR FROM CURRENT_DATE) "
              + "group by EXTRACT(MONTH FROM due_date) "
              + "order by EXTRACT(MONTH FROM due_date)",
      nativeQuery = true)
  List<Object[]> findTotalAmountInEachMonth(@Param("userId") Long userId);
}
