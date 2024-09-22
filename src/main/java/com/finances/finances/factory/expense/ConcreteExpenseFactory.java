package com.finances.finances.factory.expense;

import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class ConcreteExpenseFactory implements ExpenseFactory {

  @Override
  public Expense buildExpense(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      User user,
      FinancialCategory category,
      Supplier supplier,
      String notes) {

    return new Expense(description, amount, dueDate, user, category, supplier, notes);
  }

  @Override
  public Expense buildExpense(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      User user,
      FinancialCategory category,
      Supplier supplier,
      String notes,
      Integer referenceMonth) {

    return new Expense(
        description, amount, dueDate, user, category, supplier, notes, referenceMonth);
  }

  @Override
  public ExpenseResponseDTO buildExpenseResponseDTO(Expense expense) {

    return new ExpenseResponseDTO(
        expense.getId(),
        expense.getAmount(),
        expense.getDueDate(),
        expense.getDescription(),
        expense.getNotes(),
        expense.getCategory().getName(),
        expense.getSupplier().getName());
  }
}
