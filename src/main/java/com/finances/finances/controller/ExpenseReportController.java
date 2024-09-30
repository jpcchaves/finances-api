package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.ExpenseGroupedByCategoryDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedByMonthDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedBySupplierDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Expense Report Controller")
@SecurityRequirement(name = "Bearer Authentication")
public interface ExpenseReportController {

  @Operation(
      summary = "Gets a report of all expenses grouped by category",
      description = "This report includes the total amount of each expense grouped by category",
      responses = {
        @ApiResponse(
            description = "Ok",
            responseCode = "200",
            content =
                @Content(schema = @Schema(implementation = ExpenseGroupedByCategoryDTO.class)))
      })
  ResponseEntity<ResponseDTO<List<ExpenseGroupedByCategoryDTO>>> getExpensesGroupedByAllCategories(
      LocalDate startDate, LocalDate endDate);

  @Operation(
      summary =
          "Gets a report of all expenses grouped by category one category by passing a category name",
      description = "This report includes the total amount of the expenses related to one category",
      responses = {
        @ApiResponse(
            description = "Ok",
            responseCode = "200",
            content =
                @Content(schema = @Schema(implementation = ExpenseGroupedByCategoryDTO.class)))
      })
  ResponseEntity<ResponseDTO<ExpenseGroupedByCategoryDTO>> getExpensesGroupedByCategory(
      String categoryName, LocalDate startDate, LocalDate endDate);

  @Operation(
      summary = "Gets a report of all expenses grouped by supplier",
      description = "This report includes the total amount of each expense grouped by supplier",
      responses = {
        @ApiResponse(
            description = "Ok",
            responseCode = "200",
            content =
                @Content(schema = @Schema(implementation = ExpenseGroupedBySupplierDTO.class)))
      })
  ResponseEntity<ResponseDTO<List<ExpenseGroupedBySupplierDTO>>> getExpensesGroupedByAllSuppliers(
      LocalDate startDate, LocalDate endDate);

  @Operation(
      summary =
          "Gets a report of all expenses grouped by supplier one supplier by passing a supplier name",
      description = "This report includes the total amount of the expenses related to one supplier",
      responses = {
        @ApiResponse(
            description = "Ok",
            responseCode = "200",
            content =
                @Content(schema = @Schema(implementation = ExpenseGroupedBySupplierDTO.class)))
      })
  ResponseEntity<ResponseDTO<ExpenseGroupedBySupplierDTO>> getExpensesGroupedBySupplier(
      String supplierName, LocalDate startDate, LocalDate endDate);

  @Operation(
      summary = "Gets a report of all expenses grouped by month in the current year",
      description =
          "This reports includes the total amount of the expenses in each month in the current year",
      responses = {
        @ApiResponse(
            description = "Ok",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ExpenseGroupedByMonthDTO.class)))
      })
  ResponseEntity<ResponseDTO<List<ExpenseGroupedByMonthDTO>>> getExpensesGrouepdByMonth();
}
