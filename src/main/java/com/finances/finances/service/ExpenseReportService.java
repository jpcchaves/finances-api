package com.finances.finances.service;

import com.finances.finances.domain.dto.common.ExpenseGroupedByCategoryDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedByMonthDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedBySupplierDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseReportService {

  ResponseDTO<List<ExpenseGroupedByCategoryDTO>> getExpensesGroupedByAllCategories(
      LocalDate startDate, LocalDate endDate);

  ResponseDTO<ExpenseGroupedByCategoryDTO> getExpensesGroupedByCategory(
      String categoryName, LocalDate startDate, LocalDate endDate);

  ResponseDTO<List<ExpenseGroupedBySupplierDTO>> getExpensesGroupedByAllSuppliers(
      LocalDate startDate, LocalDate endDate);

  ResponseDTO<ExpenseGroupedBySupplierDTO> getExpensesGroupedBySupplier(
      String supplierName, LocalDate startDate, LocalDate endDate);

  ResponseDTO<List<ExpenseGroupedByMonthDTO>> getExpensesGroupedByMonth();
}
