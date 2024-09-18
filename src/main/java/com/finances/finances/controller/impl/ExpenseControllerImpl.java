package com.finances.finances.controller.impl;

import com.finances.finances.controller.ExpenseController;
import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.expense.ExpenseRequestDTO;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import com.finances.finances.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseControllerImpl implements ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseControllerImpl(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @PostMapping
  @Override
  public ResponseEntity<ResponseDTO<?>> create(@Valid @RequestBody ExpenseRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(requestDTO));
  }

  @PutMapping("/{expenseId}")
  @Override
  public ResponseEntity<ResponseDTO<?>> update(
      @PathVariable(name = "expenseId") Long expenseId,
      @Valid @RequestBody ExpenseRequestDTO requestDTO) {

    return ResponseEntity.ok(expenseService.update(expenseId, requestDTO));
  }

  @GetMapping
  @Override
  public ResponseEntity<ResponseDTO<PaginationResponseDTO<ExpenseResponseDTO>>> list(
      Pageable pageable) {

    return ResponseEntity.ok(expenseService.list(pageable));
  }

  @GetMapping("/{expenseId}")
  @Override
  public ResponseEntity<ResponseDTO<ExpenseResponseDTO>> findById(
      @PathVariable(name = "expenseId") Long expenseId) {

    return ResponseEntity.ok(expenseService.findById(expenseId));
  }

  @PostMapping(value = "/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Override
  public ResponseEntity<ResponseDTO<?>> uploadExpensesCsv(
      @RequestParam("csvFile") MultipartFile csvFile) {

    return ResponseEntity.ok(expenseService.processCSV(csvFile));
  }

  @GetMapping(value = "/download-csv-example", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<byte[]> downloadCsvExample() {

    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exemplo.csv");
    httpHeaders.set(HttpHeaders.CONTENT_TYPE, "text/csv");

    return new ResponseEntity<>(expenseService.getExampleCsv(), httpHeaders, HttpStatus.OK);
  }
}
