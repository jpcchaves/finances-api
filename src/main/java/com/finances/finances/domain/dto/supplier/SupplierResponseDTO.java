package com.finances.finances.domain.dto.supplier;

import java.io.Serial;
import java.io.Serializable;

public class SupplierResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = -6767618671421117137L;

  private Long id;

  private String name;

  public SupplierResponseDTO() {}

  public SupplierResponseDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
