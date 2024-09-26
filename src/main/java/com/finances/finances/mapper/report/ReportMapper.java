package com.finances.finances.mapper.report;

import com.finances.finances.domain.dto.common.ExpenseGroupedByMonthDTO;
import com.finances.finances.util.mapper.ReferenceMonthMapper;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

  private final ReferenceMonthMapper referenceMonthMapper;

  public ReportMapper(ReferenceMonthMapper referenceMonthMapper) {
    this.referenceMonthMapper = referenceMonthMapper;
  }

  public ExpenseGroupedByMonthDTO toDTO(Object[] reportFields) {

    BigDecimal month = (BigDecimal) reportFields[0];

    String monthName = referenceMonthMapper.getReferenceMonthByIndex(month.intValue());

    return new ExpenseGroupedByMonthDTO(monthName, (BigDecimal) reportFields[1]);
  }
}
