package com.finances.finances.domain.dto.expense;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;

public class ExpenseCsvDTO {

  @CsvBindByName(column = "Descrição", required = true)
  private String description;

  @CsvBindByName(column = "Valor", required = true)
  private String amount;

  @CsvBindByName(column = "Categoria", required = true)
  private String categoryName;

  @CsvBindByName(column = "Fornecedor", required = true)
  private String supplierName;

  @CsvBindByName(column = "Data", required = true)
  @CsvDate("dd/MM/yyyy")
  private LocalDate dueDate;

  @CsvBindByName(column = "Observações")
  private String notes;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
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
}
