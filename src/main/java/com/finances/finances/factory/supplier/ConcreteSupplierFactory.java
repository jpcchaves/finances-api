package com.finances.finances.factory.supplier;

import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ConcreteSupplierFactory implements SupplierFactory {

  @Override
  public SupplierResponseDTO buildSupplierResponseDTO(Supplier supplier) {

    return new SupplierResponseDTO(supplier.getId(), supplier.getName());
  }

  @Override
  public SupplierResponseDTO buildSupplierResponseDTO(Long supplierId, String name) {

    return new SupplierResponseDTO(supplierId, name);
  }

  @Override
  public Supplier buildSupplier(Long supplierId, String name, User user) {

    return new Supplier(supplierId, name, user);
  }

  @Override
  public Supplier buildSupplier(String name, User user) {

    return new Supplier(name, user);
  }
}
