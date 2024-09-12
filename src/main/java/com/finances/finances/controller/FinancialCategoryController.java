package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
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

@Tag(name = "Financial Category Controller")
@SecurityRequirement(name = "Bearer Authentication")
public interface FinancialCategoryController {

  @Operation(
      summary = "Creates a new financial category",
      description =
          "Creates a new financial category by passing a JSON representation of FinancialCategoryRequestDTO",
      responses = {
        @ApiResponse(
            description = "Created",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> create(FinancialCategoryRequestDTO requestDTO);

  @Operation(
      summary = "Updates a financial category",
      description =
          "Updates a financial category by passing a financial category ID and  a JSON representation of FinancialCategoryRequestDTO",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> update(
      Long financialCategoryId, FinancialCategoryRequestDTO requestDTO);

  @Operation(
      summary = "Gets financial category list",
      description = "Gets financial category list related to user",
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
  ResponseEntity<ResponseDTO<PaginationResponseDTO<FinancialCategoryResponseDTO>>> list(
      @Parameter(hidden = true) Pageable pageable);

  @Operation(
      summary = "Gets a financial category by id",
      description =
          "Gets a financial category by id by passing a  financialCategoryId as a path variable",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<FinancialCategoryResponseDTO>> findById(Long financialCategoryId);
}
