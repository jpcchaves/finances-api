package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.expense.ExpenseRequestDTO;
import com.finances.finances.domain.dto.expense.ExpenseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Expense Controller")
@SecurityRequirement(name = "Bearer Authentication")
public interface ExpenseController {

  @Operation(
      summary = "Creates a new expense",
      description = "Creates a new expense by passing a JSON representation of ExpenseRequestDTO",
      responses = {
        @ApiResponse(
            description = "Created",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> create(ExpenseRequestDTO requestDTO);

  @Operation(
      summary = "Updates a expense",
      description =
          "Updates a expense by passing a expense ID and  a JSON representation of ExpenseRequestDTO",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> update(Long expenseId, ExpenseRequestDTO requestDTO);

  @Operation(
      summary = "Gets expense list",
      description = "Gets expense list related to user",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  @PageableAsQueryParam
  ResponseEntity<ResponseDTO<PaginationResponseDTO<ExpenseResponseDTO>>> list(
      @Parameter(hidden = true) Pageable pageable);

  @Operation(
      summary = "Gets a expense by id",
      description = "Gets a expense by id by passing a expense id as a path variable",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ExpenseResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<ExpenseResponseDTO>> findById(Long expenseId);

  ResponseEntity<ResponseDTO<?>> uploadExpensesCsv(
      @ModelAttribute("csvFile") MultipartFile csvFile);
}
