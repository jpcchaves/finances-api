package com.finances.finances.domain.dto.common;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class ExpenseGroupedByCategoryDTO implements Serializable {

  @Serial private static final long serialVersionUID = 7919650818221899088L;

  private String supplier;
  private BigDecimal amount;

  public ExpenseGroupedByCategoryDTO() {}

  public ExpenseGroupedByCategoryDTO(String supplier, BigDecimal amount) {
    this.supplier = supplier;
    this.amount = amount;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
