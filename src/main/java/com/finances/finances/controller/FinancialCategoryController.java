package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface FinancialCategoryController {

  ResponseEntity<ResponseDTO<?>> create(FinancialCategoryRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<?>> update(
      Long financialCategoryId, FinancialCategoryRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<PaginationResponseDTO<FinancialCategoryResponseDTO>>> list(
      Pageable pageable);

  ResponseEntity<ResponseDTO<FinancialCategoryResponseDTO>> findById(Long financialCategoryId);
}
