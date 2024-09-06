package com.finances.finances.config.mapper.converters;

import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.factory.supplier.SupplierFactory;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplierConverters {

  private final SupplierFactory supplierFactory;

  public SupplierConverters(SupplierFactory supplierFactory) {
    this.supplierFactory = supplierFactory;
  }

  public Converter<Supplier, SupplierResponseDTO> supplierToResponseDTOConverter() {

    return context -> {
      Supplier supplier = context.getSource();

      return supplierFactory.buildSupplierResponseDTO(supplier.getId(), supplier.getName());
    };
  }
}
