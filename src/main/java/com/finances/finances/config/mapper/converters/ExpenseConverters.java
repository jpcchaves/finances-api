package com.finances.finances.config.mapper.converters;

import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.factory.expense.ExpenseFactory;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseConverters {

    private final ExpenseFactory expenseFactory;

    public ExpenseConverters(ExpenseFactory expenseFactory) {
        this.expenseFactory = expenseFactory;
    }

    public Converter<Expense, ExpenseResponseDTO>
      expenseExpenseResponseDTOConverter() {

    return context -> {

        Expense expense = context.getSource();

        return expenseFactory.buildExpenseResponseDTO(expense);
    };
  }
}
