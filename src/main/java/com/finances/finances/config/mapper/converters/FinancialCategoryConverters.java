package com.finances.finances.config.mapper.converters;

import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import org.modelmapper.Converter;

public class FinancialCategoryConverters {

  public static Converter<FinancialCategory, FinancialCategoryResponseDTO>
      financialCategoryToDTO() {

    return context -> {
      FinancialCategory financialCategory = context.getSource();

      return new FinancialCategoryResponseDTO(
          financialCategory.getId(), financialCategory.getName());
    };
  }
}
