package com.finances.finances.domain.dto.common;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class ExpenseGroupedByMonthDTO implements Serializable {

  @Serial private static final long serialVersionUID = -8144570721453457183L;
  private String month;
  private BigDecimal amount;

  public ExpenseGroupedByMonthDTO() {}

  public ExpenseGroupedByMonthDTO(String month, BigDecimal amount) {
    this.month = month;
    this.amount = amount;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
