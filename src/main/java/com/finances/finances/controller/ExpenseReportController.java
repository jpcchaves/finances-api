package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.ExpenseGroupedByCategoryDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedBySupplierDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Expense Report Controller")
@SecurityRequirement(name = "Bearer Authentication")
public interface ExpenseReportController {

  ResponseEntity<ResponseDTO<List<ExpenseGroupedByCategoryDTO>>> getExpensesGroupedByAllCategories(
      LocalDate startDate, LocalDate endDate);

  ResponseEntity<ResponseDTO<ExpenseGroupedByCategoryDTO>> getExpensesGroupedByCategory(
      String categoryName, LocalDate startDate, LocalDate endDate);

  ResponseEntity<ResponseDTO<List<ExpenseGroupedBySupplierDTO>>> getExpensesGroupedByAllSuppliers(
      LocalDate startDate, LocalDate endDate);

  ResponseEntity<ResponseDTO<ExpenseGroupedBySupplierDTO>> getExpensesGroupedBySupplier(
      String supplierName, LocalDate startDate, LocalDate endDate);
}
