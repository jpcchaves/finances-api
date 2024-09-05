package com.finances.finances.service;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import org.springframework.data.domain.Pageable;

public interface FinancialCategoryService {

  ResponseDTO<?> create(FinancialCategoryRequestDTO requestDTO);

  ResponseDTO<?> update(Long financialCategoryId, FinancialCategoryRequestDTO requestDTO);

  ResponseDTO<PaginationResponseDTO<FinancialCategoryResponseDTO>> list(Pageable pageable);

  ResponseDTO<FinancialCategoryResponseDTO> findById(Long financialCategoryId);

  ResponseDTO<?> delete(Long financialCategoryId);
}
