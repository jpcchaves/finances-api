package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.expense.ExpenseCsvDTO;
import com.finances.finances.domain.dto.expense.ExpenseRequestDTO;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.domain.entities.Expense;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.exception.ResourceNotFoundException;
import com.finances.finances.factory.expense.ExpenseFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.expense.ExpenseMapper;
import com.finances.finances.persistence.repository.ExpenseRepository;
import com.finances.finances.persistence.repository.FinancialCategoryRepository;
import com.finances.finances.persistence.repository.SupplierRepository;
import com.finances.finances.service.ExpenseService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

  private final ExpenseRepository expenseRepository;
  private final FinancialCategoryRepository financialCategoryRepository;
  private final SupplierRepository supplierRepository;
  private final AuthHelper authHelper;
  private final ExpenseFactory expenseFactory;
  private final ExpenseMapper expenseMapper;

  public ExpenseServiceImpl(
      ExpenseRepository expenseRepository,
      FinancialCategoryRepository financialCategoryRepository,
      SupplierRepository supplierRepository,
      AuthHelper authHelper,
      ExpenseFactory expenseFactory,
      ExpenseMapper expenseMapper) {
    this.expenseRepository = expenseRepository;
    this.financialCategoryRepository = financialCategoryRepository;
    this.supplierRepository = supplierRepository;
    this.authHelper = authHelper;
    this.expenseFactory = expenseFactory;
    this.expenseMapper = expenseMapper;
  }

  @Override
  @Transactional
  public ResponseDTO<?> create(ExpenseRequestDTO requestDTO) {

    FinancialCategory financialCategory =
        financialCategoryRepository
            .findByName(authHelper.getUserDetails().getId(), requestDTO.getCategory())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            "Categoria não encontrada com o nome informado: %s",
                            requestDTO.getCategory())));

    Supplier supplier =
        supplierRepository
            .findByName(authHelper.getUserDetails().getId(), requestDTO.getSupplier())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            "Fornecedor não encontrado com o nome informado %s",
                            requestDTO.getSupplier())));

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
  @Transactional
  public ResponseDTO<?> update(Long expenseId, ExpenseRequestDTO requestDTO) {

    Expense expense =
        expenseRepository
            .findById(authHelper.getUserDetails().getId(), expenseId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Despesa não encontrada com o ID informado!"));

    if (!Objects.equals(expense.getCategory().getName(), requestDTO.getCategory())) {

      FinancialCategory financialCategory =
          financialCategoryRepository
              .findByName(authHelper.getUserDetails().getId(), requestDTO.getCategory())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          String.format(
                              "Categoria não encontrada com o nome informado: %s",
                              requestDTO.getCategory())));

      expense.setCategory(financialCategory);
    }

    if (!Objects.equals(expense.getSupplier().getName(), requestDTO.getSupplier())) {

      Supplier supplier =
          supplierRepository
              .findByName(authHelper.getUserDetails().getId(), requestDTO.getSupplier())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          String.format(
                              "Fornecedor não encontrado com o nome informado %s",
                              requestDTO.getSupplier())));

      expense.setSupplier(supplier);
    }

    expense.setDescription(requestDTO.getDescription());
    expense.setAmount(requestDTO.getAmount());
    expense.setDueDate(requestDTO.getDueDate());
    expense.setNotes(requestDTO.getNotes());

    expense = expenseRepository.save(expense);

    ExpenseResponseDTO expenseResponseDTO = expenseFactory.buildExpenseResponseDTO(expense);

    return ResponseDTO.withData(expenseResponseDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseDTO<PaginationResponseDTO<ExpenseResponseDTO>> list(Pageable pageable) {

    Page<Expense> expensesPage =
        expenseRepository.findAll(authHelper.getUserDetails().getId(), pageable);

    List<ExpenseResponseDTO> expenseResponseDTOList =
        expenseMapper.toDTO(expensesPage.getContent());

    PaginationResponseDTO<ExpenseResponseDTO> paginationResponseDTO =
        new PaginationResponseDTO<ExpenseResponseDTO>()
            .builder()
            .setContent(expenseResponseDTOList)
            .setPage(expensesPage.getNumber())
            .setSize(expensesPage.getSize())
            .setTotalPages(expensesPage.getTotalPages())
            .setTotalElements(expensesPage.getTotalElements())
            .build();

    return ResponseDTO.withData(paginationResponseDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseDTO<ExpenseResponseDTO> findById(Long expenseId) {

    Expense expense =
        expenseRepository
            .findById(authHelper.getUserDetails().getId(), expenseId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Despesa não encontrada com o ID informado!"));

    ExpenseResponseDTO responseDTO = expenseMapper.toDTO(expense);

    return ResponseDTO.withData(responseDTO);
  }

  @Override
  public ResponseDTO<?> delete(Long expenseId) {
    return null;
  }

  @Transactional
  @Override
  public ResponseDTO<?> processCSV(MultipartFile csvFile) {

    List<Expense> expenses = new ArrayList<>();

    User user = authHelper.getUserDetails();
    Long userId = user.getId();

    try (InputStreamReader reader = new InputStreamReader(csvFile.getInputStream())) {

      CsvToBean<ExpenseCsvDTO> csvDTOS =
          new CsvToBeanBuilder<ExpenseCsvDTO>(reader)
              .withType(ExpenseCsvDTO.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build();

      List<ExpenseCsvDTO> expenseCsvDTOList = csvDTOS.parse();

      for (int i = 0; i < expenseCsvDTOList.size(); i++) {

        ExpenseCsvDTO expenseCsvDTO = expenseCsvDTOList.get(i);

        int lineNumber = i + 2;

        BigDecimal amount = new BigDecimal(expenseCsvDTO.getAmount());
        LocalDate dueDate = expenseCsvDTO.getDueDate();

        FinancialCategory financialCategory =
            financialCategoryRepository
                .findByName(userId, expenseCsvDTO.getCategoryName().toLowerCase())
                .orElseThrow(
                    () ->
                        new ResourceNotFoundException(
                            String.format(
                                "Categoria não encontrada na linha: %s. Categoria: %s",
                                lineNumber, expenseCsvDTO.getCategoryName())));

        Supplier supplier =
            supplierRepository
                .findByName(userId, expenseCsvDTO.getSupplierName().toLowerCase())
                .orElseThrow(
                    () ->
                        new ResourceNotFoundException(
                            String.format(
                                "Fornecedor não encontrado na linha: %s. Fornecedor: %s",
                                lineNumber, expenseCsvDTO.getSupplierName())));

        expenses.add(
            expenseFactory.buildExpense(
                expenseCsvDTO.getDescription(),
                amount,
                dueDate,
                user,
                financialCategory,
                supplier,
                expenseCsvDTO.getNotes()));
      }

    } catch (IOException ex) {

      logger.error("Ocorreu um erro interno ao processar o CSV de despesas");

      logger.error(Arrays.toString(ex.getStackTrace()));

      throw new BadRequestException("Ocorreu um erro processando o csv.");
    }

    expenseRepository.saveAll(expenses);

    return ResponseDTO.withMessage(
        "Arquivo processado com sucesso! Total de despesas processadas: " + expenses.size());
  }
}
