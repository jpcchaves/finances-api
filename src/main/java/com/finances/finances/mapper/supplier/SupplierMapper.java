package com.finances.finances.mapper.supplier;

import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import java.util.ArrayList;
import java.util.List;

public class SupplierMapper {

  public static List<SupplierResponseDTO> toDTO(List<Supplier> expensesList) {

    List<SupplierResponseDTO> expenseResponseDTOS = new ArrayList<>();

    for (Supplier expense : expensesList) {

      expenseResponseDTOS.add(new SupplierResponseDTO(expense.getId(), expense.getName()));
    }

    return expenseResponseDTOS;
  }
}
