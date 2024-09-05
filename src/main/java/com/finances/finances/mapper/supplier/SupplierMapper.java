package com.finances.finances.mapper.supplier;

import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.util.mapper.MapperUtil;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

  private final MapperUtil mapperUtil;

  public SupplierMapper(MapperUtil mapperUtil) {
    this.mapperUtil = mapperUtil;
  }

  public List<SupplierResponseDTO> toDTO(List<Supplier> suppliersList) {

    return mapperUtil.mapObjects(suppliersList, SupplierResponseDTO.class);
  }
}
