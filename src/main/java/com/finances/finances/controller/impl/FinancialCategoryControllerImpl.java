package com.finances.finances.controller.impl;

import com.finances.finances.controller.FinancialCategoryController;
import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.service.FinancialCategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/financial-categories")
public class FinancialCategoryControllerImpl implements FinancialCategoryController {

  private final FinancialCategoryService financialCategoryService;

  public FinancialCategoryControllerImpl(FinancialCategoryService financialCategoryService) {
    this.financialCategoryService = financialCategoryService;
  }

  @PostMapping
  @Override
  public ResponseEntity<ResponseDTO<?>> create(
      @Valid @RequestBody FinancialCategoryRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(financialCategoryService.create(requestDTO));
  }

  @PutMapping("/{financialCategoryId}")
  @Override
  public ResponseEntity<ResponseDTO<?>> update(
      @PathVariable(name = "financialCategoryId") Long financialCategoryId,
      @Valid @RequestBody FinancialCategoryRequestDTO requestDTO) {

    return ResponseEntity.ok(financialCategoryService.update(financialCategoryId, requestDTO));
  }

  @GetMapping
  @Override
  public ResponseEntity<ResponseDTO<PaginationResponseDTO<FinancialCategoryResponseDTO>>> list(
      Pageable pageable) {

    return ResponseEntity.ok(financialCategoryService.list(pageable));
  }

  @GetMapping("/{financialCategoryId}")
  @Override
  public ResponseEntity<ResponseDTO<FinancialCategoryResponseDTO>> findById(
      @PathVariable(name = "financialCategoryId") Long financialCategoryId) {

    return ResponseEntity.ok(financialCategoryService.findById(financialCategoryId));
  }
}
