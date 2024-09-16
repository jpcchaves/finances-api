package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.ExpenseGroupedByCategoryDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedBySupplierDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.exception.ResourceNotFoundException;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.persistence.repository.ExpenseRepository;
import com.finances.finances.persistence.repository.FinancialCategoryRepository;
import com.finances.finances.persistence.repository.SupplierRepository;
import com.finances.finances.service.ExpenseReportService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExpenseReportServiceImpl implements ExpenseReportService {

  private final ExpenseRepository expenseRepository;
  private final FinancialCategoryRepository financialCategoryRepository;
  private final SupplierRepository supplierRepository;
  private final AuthHelper authHelper;

  public ExpenseReportServiceImpl(
      ExpenseRepository expenseRepository,
      FinancialCategoryRepository financialCategoryRepository,
      SupplierRepository supplierRepository,
      AuthHelper authHelper) {
    this.expenseRepository = expenseRepository;
    this.financialCategoryRepository = financialCategoryRepository;
    this.supplierRepository = supplierRepository;
    this.authHelper = authHelper;
  }

  @Override
  public ResponseDTO<List<ExpenseGroupedByCategoryDTO>> getExpensesGroupedByAllCategories(
      LocalDate startDate, LocalDate endDate) {

    List<Object[]> expensesGroupedByCategory =
        expenseRepository.findTotalAmountByAllCategories(
            authHelper.getUserDetails().getId(), startDate, endDate);

    List<ExpenseGroupedByCategoryDTO> expenseGroupedByCategoryDTOSList =
        expensesGroupedByCategory.stream()
            .map(
                exGrouped ->
                    new ExpenseGroupedByCategoryDTO(
                        (String) exGrouped[0], (BigDecimal) exGrouped[1]))
            .toList();

    return ResponseDTO.withData(expenseGroupedByCategoryDTOSList);
  }

  @Override
  public ResponseDTO<ExpenseGroupedByCategoryDTO> getExpensesGroupedByCategory(
      String categoryName, LocalDate startDate, LocalDate endDate) {

    Long userId = authHelper.getUserDetails().getId();

    FinancialCategory category =
        financialCategoryRepository
            .findByName(userId, categoryName)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));

    List<Object[]> expenseGroupedByCategory =
        expenseRepository.findTotalAmountByCategory(userId, category.getId(), startDate, endDate);

    Object[] result = expenseGroupedByCategory.get(0);

    BigDecimal totalAmount = (BigDecimal) result[1];

    ExpenseGroupedByCategoryDTO expenseGroupedByCategoryDTO =
        new ExpenseGroupedByCategoryDTO(category.getName(), totalAmount);

    return ResponseDTO.withData(expenseGroupedByCategoryDTO);
  }

  @Override
  public ResponseDTO<List<ExpenseGroupedBySupplierDTO>> getExpensesGroupedByAllSuppliers(
      LocalDate startDate, LocalDate endDate) {

    List<Object[]> expenseGroupedBySupplier =
        expenseRepository.findTotalAmountByAllSuppliers(
            authHelper.getUserDetails().getId(), startDate, endDate);

    List<ExpenseGroupedBySupplierDTO> expenseGroupedBySupplierDTOS =
        expenseGroupedBySupplier.stream()
            .map(
                exGrouped ->
                    new ExpenseGroupedBySupplierDTO(
                        (String) exGrouped[0], (BigDecimal) exGrouped[1]))
            .toList();

    return ResponseDTO.withData(expenseGroupedBySupplierDTOS);
  }

  @Override
  public ResponseDTO<ExpenseGroupedBySupplierDTO> getExpensesGroupedBySupplier(
      String supplierName, LocalDate startDate, LocalDate endDate) {

    Long userId = authHelper.getUserDetails().getId();

    Supplier supplier =
        supplierRepository
            .findByName(userId, supplierName)
            .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado!"));

    List<Object[]> expensesGroupedBySupplier =
        expenseRepository.findTotalAmountBySupplier(userId, supplier.getId(), startDate, endDate);

    Object[] result = expensesGroupedBySupplier.get(0);

    BigDecimal totalAmount = (BigDecimal) result[1];

    ExpenseGroupedBySupplierDTO expenseGroupedBySupplierDTO =
        new ExpenseGroupedBySupplierDTO(supplier.getName(), totalAmount);

    return ResponseDTO.withData(expenseGroupedBySupplierDTO);
  }
}
