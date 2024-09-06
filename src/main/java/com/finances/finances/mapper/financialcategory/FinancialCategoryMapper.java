package com.finances.finances.mapper.financialcategory;

import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.util.mapper.MapperUtil;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FinancialCategoryMapper {

  private final MapperUtil mapperUtil;

  public FinancialCategoryMapper(MapperUtil mapperUtil) {
    this.mapperUtil = mapperUtil;
  }

  public List<FinancialCategoryResponseDTO> toDTO(List<FinancialCategory> financialCategories) {

    return mapperUtil.mapObjects(financialCategories, FinancialCategoryResponseDTO.class);
  }

  public FinancialCategoryResponseDTO toDTO(FinancialCategory financialCategory) {

    return mapperUtil.mapObject(financialCategory, FinancialCategoryResponseDTO.class);
  }
}
