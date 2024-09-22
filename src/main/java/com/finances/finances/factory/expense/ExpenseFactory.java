package com.finances.finances.factory.expense;

import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExpenseFactory {

  Expense buildExpense(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      User user,
      FinancialCategory category,
      Supplier supplier,
      String notes);

  Expense buildExpense(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      User user,
      FinancialCategory category,
      Supplier supplier,
      String notes,
      Integer referenceMonth);

  ExpenseResponseDTO buildExpenseResponseDTO(Expense expense);
}
