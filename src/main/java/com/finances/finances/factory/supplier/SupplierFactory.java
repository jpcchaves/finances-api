package com.finances.finances.factory.supplier;

import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;

public interface SupplierFactory {

  SupplierResponseDTO buildSupplierResponseDTO(Supplier supplier);

  SupplierResponseDTO buildSupplierResponseDTO(Long supplierId, String name);

  Supplier buildSupplier(Long supplierId, String name, User user);

  Supplier buildSupplier(String name, User user);
}
