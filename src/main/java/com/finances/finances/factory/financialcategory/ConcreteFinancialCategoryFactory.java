package com.finances.finances.factory.financialcategory;

import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ConcreteFinancialCategoryFactory implements FinancialCategoryFactory {

  @Override
  public FinancialCategoryResponseDTO buildFinancialCategoryResponseDTO(
      Long financialCategoryId, String name) {

    return new FinancialCategoryResponseDTO(financialCategoryId, name);
  }

  @Override
  public FinancialCategoryResponseDTO buildFinancialCategoryResponseDTO(
      FinancialCategory financialCategory) {

    return new FinancialCategoryResponseDTO(financialCategory.getId(), financialCategory.getName());
  }

  @Override
  public FinancialCategory buildFinancialCategory(String name, User user) {

    return new FinancialCategory(name, user);
  }
}
