package com.finances.finances.config.mapper;

import com.finances.finances.config.mapper.converters.ExpenseConverters;
import com.finances.finances.config.mapper.converters.SupplierConverters;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.domain.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  private final SupplierConverters supplierConverters;
  private final ExpenseConverters expenseConverters;

  public ModelMapperConfig(
      SupplierConverters supplierConverters, ExpenseConverters expenseConverters) {
    this.supplierConverters = supplierConverters;
    this.expenseConverters = expenseConverters;
  }

  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();

    modelMapper.addConverter(
        supplierConverters.supplierToResponseDTOConverter(),
        Supplier.class,
        SupplierResponseDTO.class);

    modelMapper.addConverter(
        expenseConverters.expenseExpenseResponseDTOConverter(),
        Expense.class,
        ExpenseResponseDTO.class);

    return modelMapper;
  }
}
