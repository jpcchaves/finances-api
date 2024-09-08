package com.finances.finances.factory.financialcategory;

import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.User;

public interface FinancialCategoryFactory {

  FinancialCategoryResponseDTO buildFinancialCategoryResponseDTO(
      Long financialCategoryId, String name);

  FinancialCategoryResponseDTO buildFinancialCategoryResponseDTO(
      FinancialCategory financialCategory);

  FinancialCategory buildFinancialCategory(String name, User user);
}
