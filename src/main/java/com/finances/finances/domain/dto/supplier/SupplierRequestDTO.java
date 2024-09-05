package com.finances.finances.domain.dto.supplier;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

public class SupplierRequestDTO implements Serializable {

  @Serial private static final long serialVersionUID = 7373587167671651126L;

  @NotBlank(message = "O nome do fornecedor é obrigatório!")
  @Length(min = 3, message = "O fornecedor precisa ter um nome maior do que 3 caracteres!")
  private String name;

  public SupplierRequestDTO() {}

  public SupplierRequestDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
