package com.finances.finances.domain.dto.financialcategory;

import java.io.Serial;
import java.io.Serializable;

public class FinancialCategoryResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = 5187617291993914981L;

  private Long id;

  private String name;

  public FinancialCategoryResponseDTO() {}

  public FinancialCategoryResponseDTO(Long id, String name) {
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
