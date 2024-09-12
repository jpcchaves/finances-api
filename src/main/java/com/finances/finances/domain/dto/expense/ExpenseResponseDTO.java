package com.finances.finances.domain.dto.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = -7538269307671060249L;

  private Long id;
  private BigDecimal amount;

  @JsonFormat(pattern = "dd-MM-yyyy", timezone = "America/Sao_Paulo")
  private LocalDate dueDate;

  private String notes;
  private String category;
  private String supplier;

  public ExpenseResponseDTO() {}

  public ExpenseResponseDTO(
      Long id,
      BigDecimal amount,
      LocalDate dueDate,
      String notes,
      String category,
      String supplier) {
    this.id = id;
    this.amount = amount;
    this.dueDate = dueDate;
    this.notes = notes;
    this.category = category;
    this.supplier = supplier;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
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
