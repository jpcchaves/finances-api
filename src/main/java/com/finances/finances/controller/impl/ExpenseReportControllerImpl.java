package com.finances.finances.controller.impl;

import com.finances.finances.controller.ExpenseReportController;
import com.finances.finances.domain.dto.common.ExpenseGroupedByCategoryDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedByMonthDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedBySupplierDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.service.ExpenseReportService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense-reports")
public class ExpenseReportControllerImpl implements ExpenseReportController {

  private final ExpenseReportService expenseReportService;

  public ExpenseReportControllerImpl(ExpenseReportService expenseReportService) {
    this.expenseReportService = expenseReportService;
  }

  @GetMapping("/by-category")
  @Override
  public ResponseEntity<ResponseDTO<List<ExpenseGroupedByCategoryDTO>>>
      getExpensesGroupedByAllCategories(
          @RequestParam(name = "startDate") LocalDate startDate,
          @RequestParam(name = "endDate") LocalDate endDate) {

    return ResponseEntity.ok(
        expenseReportService.getExpensesGroupedByAllCategories(startDate, endDate));
  }

  @GetMapping("/by-category/{categoryName}")
  @Override
  public ResponseEntity<ResponseDTO<ExpenseGroupedByCategoryDTO>> getExpensesGroupedByCategory(
      @PathVariable(name = "categoryName") String categoryName,
      @RequestParam(name = "startDate") LocalDate startDate,
      @RequestParam(name = "endDate") LocalDate endDate) {

    return ResponseEntity.ok(
        expenseReportService.getExpensesGroupedByCategory(categoryName, startDate, endDate));
  }

  @GetMapping("/by-supplier")
  @Override
  public ResponseEntity<ResponseDTO<List<ExpenseGroupedBySupplierDTO>>>
      getExpensesGroupedByAllSuppliers(
          @RequestParam(name = "startDate") LocalDate startDate,
          @RequestParam(name = "endDate") LocalDate endDate) {

    return ResponseEntity.ok(
        expenseReportService.getExpensesGroupedByAllSuppliers(startDate, endDate));
  }

  @GetMapping("/by-supplier/{supplierName}")
  @Override
  public ResponseEntity<ResponseDTO<ExpenseGroupedBySupplierDTO>> getExpensesGroupedBySupplier(
      @PathVariable(name = "supplierName") String supplierName,
      @RequestParam(name = "startDate") LocalDate startDate,
      @RequestParam(name = "endDate") LocalDate endDate) {

    return ResponseEntity.ok(
        expenseReportService.getExpensesGroupedBySupplier(supplierName, startDate, endDate));
  }

  @Override
  public ResponseEntity<ResponseDTO<List<ExpenseGroupedByMonthDTO>>> getExpensesGrouepdByMonth() {

    return ResponseEntity.ok(expenseReportService.getExpensesGroupedByMonth());
  }
}
