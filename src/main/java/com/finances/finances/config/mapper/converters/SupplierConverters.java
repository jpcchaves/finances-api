package com.finances.finances.config.mapper.converters;

import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import org.modelmapper.Converter;

public class SupplierConverters {

  public static Converter<Supplier, SupplierResponseDTO> supplierToResponseDTOConverter() {

    return context -> {
      Supplier supplier = context.getSource();

      return new SupplierResponseDTO(supplier.getId(), supplier.getName());
    };
  }
}
