package com.finances.finances.config.mapper.converters;

import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.factory.financialcategory.FinancialCategoryFactory;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinancialCategoryConverters {

  private final FinancialCategoryFactory financialCategoryFactory;

  public FinancialCategoryConverters(FinancialCategoryFactory financialCategoryFactory) {
    this.financialCategoryFactory = financialCategoryFactory;
  }

  public Converter<FinancialCategory, FinancialCategoryResponseDTO> financialCategoryToDTO() {

    return context -> {
      FinancialCategory financialCategory = context.getSource();

      return financialCategoryFactory.buildFinancialCategoryResponseDTO(
          financialCategory.getId(), financialCategory.getName());
    };
  }
}
