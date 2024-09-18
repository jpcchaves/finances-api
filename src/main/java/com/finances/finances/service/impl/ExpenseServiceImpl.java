package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

  @Override
  public ResponseDTO<?> processCSV(MultipartFile csvFile) {

    DateTimeFormatter dateInputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DateTimeFormatter dbDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    List<Expense> expenses = new ArrayList<>();

    User user = authHelper.getUserDetails();
    Long userId = user.getId();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {

      String line;
      int lineNumber = 0;

      while ((line = br.readLine()) != null) {

        lineNumber++;

        if (lineNumber == 1) continue;

        String[] fields = line.split(",");

        if (fields.length < 5) {

          throw new BadRequestException(
              "Arquivo com quantidade incorreta de colunas! Verifique o arquivo e tente novamente.");
        }

        String description = fields[0].trim();
        String strAmount = fields[1].trim();
        String categoryName = fields[2].trim().toLowerCase();
        String supplierName = fields[3].trim().toLowerCase();
        String strDueDate = fields[4].trim();
        String notes = fields.length > 5 ? fields[5].trim() : "";

        if (description.isEmpty()
            || strAmount.isEmpty()
            || categoryName.isEmpty()
            || supplierName.isEmpty()
            || strDueDate.isEmpty()) {

          throw new BadRequestException("Campos obrigatórios faltando na linha: " + lineNumber);
        }

        BigDecimal amount = new BigDecimal(strAmount);
        LocalDate dueDate = LocalDate.parse(strDueDate, dateInputFormatter);

        int finalLineNumber = lineNumber;

        FinancialCategory financialCategory =
            financialCategoryRepository
                .findByName(userId, categoryName)
                .orElseThrow(
                    () ->
                        new ResourceNotFoundException(
                            String.format(
                                "Categoria não encontrada na linha: %s. Categoria: %s",
                                finalLineNumber, categoryName)));

        Supplier supplier =
            supplierRepository
                .findByName(userId, supplierName)
                .orElseThrow(
                    () ->
                        new ResourceNotFoundException(
                            String.format(
                                "Fornecedor não encontrado na linha: %s. Fornecedor: %s",
                                finalLineNumber, supplierName)));

        expenses.add(
            expenseFactory.buildExpense(
                description,
                amount,
                LocalDate.parse(dueDate.toString(), dbDateFormatter),
                authHelper.getUserDetails(),
                financialCategory,
                supplier,
                notes));
      }

    } catch (Exception ex) {

      ex.printStackTrace();

      logger.error("Ocorreu um erro interno ao processar o CSV de despesas");

      throw new BadRequestException("Ocorreu um erro processando o csv.");
    }

    expenseRepository.saveAll(expenses);

    return ResponseDTO.withMessage(
        "Arquivo processado com sucesso! Total de despesas processadas: " + expenses.size());
  }
}
