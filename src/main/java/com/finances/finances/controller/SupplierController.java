package com.finances.finances.controller;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SupplierController {
  ResponseEntity<ResponseDTO<?>> create(SupplierRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<?>> update(Long supplierId, SupplierRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<PaginationResponseDTO<SupplierResponseDTO>>> list(Pageable pageable);

  ResponseEntity<ResponseDTO<SupplierResponseDTO>> findById(Long supplierId);
}
