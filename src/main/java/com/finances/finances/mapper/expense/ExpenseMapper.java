package com.finances.finances.mapper.expense;

import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.util.mapper.MapperUtil;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

  private final MapperUtil mapperUtil;

  public ExpenseMapper(MapperUtil mapperUtil) {
    this.mapperUtil = mapperUtil;
  }

  public List<ExpenseResponseDTO> toDTO(List<Expense> expenseList) {

    return mapperUtil.mapObjects(expenseList, ExpenseResponseDTO.class);
  }

  public ExpenseResponseDTO toDTO(Expense expense) {

    return mapperUtil.mapObject(expense, ExpenseResponseDTO.class);
  }
}
