package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.expense.ExpenseRequestDTO;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ExpenseController {

  ResponseEntity<ResponseDTO<?>> create(ExpenseRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<?>> update(Long expenseId, ExpenseRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<PaginationResponseDTO<ExpenseResponseDTO>>> list(Pageable pageable);

  ResponseEntity<ResponseDTO<ExpenseResponseDTO>> findById(Long expenseId);
}
