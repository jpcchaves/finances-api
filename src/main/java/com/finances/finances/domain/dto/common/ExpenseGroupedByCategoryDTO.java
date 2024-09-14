package com.finances.finances.domain.dto.common;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class ExpenseGroupedByCategoryDTO implements Serializable {

  @Serial private static final long serialVersionUID = 7919650818221899088L;

  private String category;
  private BigDecimal amount;

  public ExpenseGroupedByCategoryDTO() {}

  public ExpenseGroupedByCategoryDTO(String category, BigDecimal amount) {
    this.category = category;
    this.amount = amount;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
