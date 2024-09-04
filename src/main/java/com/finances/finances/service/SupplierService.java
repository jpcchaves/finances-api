package com.finances.finances.service;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import org.springframework.data.domain.Pageable;

public interface SupplierService {

  ResponseDTO<?> create(SupplierRequestDTO requestDTO);

  ResponseDTO<?> update(Long supplierId, SupplierRequestDTO requestDTO);

  ResponseDTO<PaginationResponseDTO<SupplierResponseDTO>> list(Pageable pageable);

  ResponseDTO<SupplierResponseDTO> findById(Long supplierId);

  ResponseDTO<?> delete(Long supplierId);
}
