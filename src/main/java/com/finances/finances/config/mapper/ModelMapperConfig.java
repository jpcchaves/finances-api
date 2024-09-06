package com.finances.finances.config.mapper;

import com.finances.finances.config.mapper.converters.SupplierConverters;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  private final SupplierConverters supplierConverters;

  public ModelMapperConfig(SupplierConverters supplierConverters) {
    this.supplierConverters = supplierConverters;
  }

  @Bean
  public ModelMapper modelMapper() {

    ModelMapper modelMapper = new ModelMapper();

    modelMapper.addConverter(
        supplierConverters.supplierToResponseDTOConverter(),
        Supplier.class,
        SupplierResponseDTO.class);

    return modelMapper;
  }
}
