package com.finances.finances.controller.impl;

import com.finances.finances.controller.SupplierController;
import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierControllerImpl implements SupplierController {

  private final SupplierService supplierService;

  public SupplierControllerImpl(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @PostMapping
  @Override
  public ResponseEntity<ResponseDTO<?>> create(@Valid @RequestBody SupplierRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.create(requestDTO));
  }

  @PutMapping("/{supplierId}")
  @Override
  public ResponseEntity<ResponseDTO<?>> update(
      @PathVariable(name = "supplierId") Long supplierId,
      @Valid @RequestBody SupplierRequestDTO requestDTO) {

    return ResponseEntity.ok(supplierService.update(supplierId, requestDTO));
  }

  @GetMapping
  @Override
  public ResponseEntity<ResponseDTO<PaginationResponseDTO<SupplierResponseDTO>>> list(
      Pageable pageable) {

    return ResponseEntity.ok(supplierService.list(pageable));
  }

  @GetMapping("/{supplierId}")
  @Override
  public ResponseEntity<ResponseDTO<SupplierResponseDTO>> findById(
      @PathVariable(name = "supplierId") Long supplierId) {

    return ResponseEntity.ok(supplierService.findById(supplierId));
  }
}
