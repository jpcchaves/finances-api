package com.finances.finances.service;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.expense.ExpenseRequestDTO;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ExpenseService {

  ResponseDTO<?> create(ExpenseRequestDTO requestDTO);

  ResponseDTO<?> update(Long expenseId, ExpenseRequestDTO requestDTO);

  ResponseDTO<PaginationResponseDTO<ExpenseResponseDTO>> list(Pageable pageable);

  ResponseDTO<ExpenseResponseDTO> findById(Long expenseId);

  ResponseDTO<?> delete(Long expenseId);

  ResponseDTO<?> processCSV(MultipartFile csvFile);
}
