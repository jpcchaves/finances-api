package com.finances.finances.domain.dto.expense;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDTO implements Serializable {

  @Serial private static final long serialVersionUID = 887269904107595677L;

  @NotBlank(message = "A descrição é obrigatória!")
  private String description;

  @Min(value = 0, message = "O valor é obrigatório!")
  @Positive(message = "Valor inválido!")
  private BigDecimal amount;

  @NotNull(message = "A data da despesa é obrigatória!")
  private LocalDate dueDate;

  private String notes;

  @NotBlank(message = "A categoria é obrigatória!")
  private String category;

  @NotBlank(message = "O fornecedor é obrigatório!")
  private String supplier;

  public ExpenseRequestDTO() {}

  public ExpenseRequestDTO(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      String notes,
      String category,
      String supplier) {
    this.description = description;
    this.amount = amount;
    this.dueDate = dueDate;
    this.notes = notes;
    this.category = category;
    this.supplier = supplier;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public java.math.BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(java.math.BigDecimal amount) {
    this.amount = amount;
  }

  public java.time.LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(java.time.LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }
}
