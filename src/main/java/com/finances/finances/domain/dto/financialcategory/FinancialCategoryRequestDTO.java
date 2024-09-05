package com.finances.finances.domain.dto.financialcategory;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

public class FinancialCategoryRequestDTO implements Serializable {

  @Serial private static final long serialVersionUID = -222214455720102356L;

  @NotBlank(message = "O nome da categoria financeira é obrigatório!")
  @Length(min = 3, message = "A categoria precisa ter um nome maior do que 3 caracteres!")
  private String name;

  public FinancialCategoryRequestDTO() {}

  public FinancialCategoryRequestDTO(String name) {
    this.name = name;
  }

  public @NotBlank(message = "O nome da categoria financeira é obrigatório!") @Length(
      min = 3,
      message = "A categoria precisa ter um nome maior do que 3 caracteres!") String getName() {
    return name;
  }

  public void setName(
      @NotBlank(message = "O nome da categoria financeira é obrigatório!")
          @Length(min = 3, message = "A categoria precisa ter um nome maior do que 3 caracteres!")
          String name) {
    this.name = name;
  }
}
