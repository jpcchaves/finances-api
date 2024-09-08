package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.expense.ExpenseRequestDTO;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.exception.ResourceNotFoundException;
import com.finances.finances.factory.expense.ExpenseFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.persistence.repository.ExpenseRepository;
import com.finances.finances.persistence.repository.FinancialCategoryRepository;
import com.finances.finances.persistence.repository.SupplierRepository;
import com.finances.finances.service.ExpenseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final FinancialCategoryRepository financialCategoryRepository;
  private final SupplierRepository supplierRepository;
  private final AuthHelper authHelper;
  private final ExpenseFactory expenseFactory;

  public ExpenseServiceImpl(
      ExpenseRepository expenseRepository,
      FinancialCategoryRepository financialCategoryRepository,
      SupplierRepository supplierRepository,
      AuthHelper authHelper,
      ExpenseFactory expenseFactory) {
    this.expenseRepository = expenseRepository;
    this.financialCategoryRepository = financialCategoryRepository;
    this.supplierRepository = supplierRepository;
    this.authHelper = authHelper;
    this.expenseFactory = expenseFactory;
  }

  @Override
  public ResponseDTO<?> create(ExpenseRequestDTO requestDTO) {

    FinancialCategory financialCategory =
        financialCategoryRepository
            .findByName(requestDTO.getCategory())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format("Categoria não encontrada com o nome informado: %s", requestDTO.getCategory())));

    Supplier supplier =
        supplierRepository
            .findByName(requestDTO.getSupplier())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format("Fornecedor não encontrado com o nome informado %s", requestDTO.getSupplier())));

    Expense expense =
        expenseFactory.buildExpense(
            requestDTO.getDescription(),
            requestDTO.getAmount(),
            requestDTO.getDueDate(),
            authHelper.getUserDetails(),
            financialCategory,
            supplier,
            requestDTO.getNotes());

    expense = expenseRepository.save(expense);

    ExpenseResponseDTO expenseResponseDTO = expenseFactory.buildExpenseResponseDTO(expense);

    return ResponseDTO.withData(expenseResponseDTO);
  }

  @Override
  public ResponseDTO<?> update(Long expenseId, ExpenseRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ResponseDTO<PaginationResponseDTO<ExpenseResponseDTO>> list(Pageable pageable) {
    return null;
  }

  @Override
  public ResponseDTO<ExpenseResponseDTO> findById(Long expenseId) {
    return null;
  }

  @Override
  public ResponseDTO<?> delete(Long expenseId) {
    return null;
  }
}
