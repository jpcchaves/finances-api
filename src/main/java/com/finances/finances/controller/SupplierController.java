package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
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

@Tag(name = "Supplier Controller")
@SecurityRequirement(name = "Bearer Authentication")
public interface SupplierController {

  @Operation(
      summary = "Creates a new supplier",
      description = "Creates a new supplier by passing a JSON representation of SupplierRequestDTO",
      responses = {
        @ApiResponse(
            description = "Created",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> create(SupplierRequestDTO requestDTO);

  @Operation(
      summary = "Updates a supplier",
      description =
          "Updates a supplier by passing a supplier ID and  a JSON representation of SupplierRequestDTO",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> update(Long supplierId, SupplierRequestDTO requestDTO);

  @Operation(
      summary = "Gets supplier list",
      description = "Gets supplier list related to user",
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
  ResponseEntity<ResponseDTO<PaginationResponseDTO<SupplierResponseDTO>>> list(
      @Parameter(hidden = true) Pageable pageable);

  @Operation(
      summary = "Gets a supplier by id",
      description = "Gets a supplier by id by passing a supplier id as a path variable",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<SupplierResponseDTO>> findById(Long supplierId);
}
