package com.finances.finances.strategy.monthconverter;

import com.finances.finances.exception.BadRequestException;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class PortugueseToEnglishMonthConverterStrategy implements MonthConverterStrategy {

  private static final Map<String, String> monthMap;

  static {
    monthMap = new HashMap<>(12);
    monthMap.put("janeiro", "JANUARY");
    monthMap.put("fevereiro", "FEBRUARY");
    monthMap.put("março", "MARCH");
    monthMap.put("abril", "APRIL");
    monthMap.put("maio", "MAY");
    monthMap.put("junho", "JUNE");
    monthMap.put("julho", "JULY");
    monthMap.put("agosto", "AUGUST");
    monthMap.put("setembro", "SEPTEMBER");
    monthMap.put("outubro", "OCTOBER");
    monthMap.put("novembro", "NOVEMBER");
    monthMap.put("dezembro", "DECEMBER");
  }

  @Override
  public String convert(String monthName) {

    String normalizedMonthName = monthName.trim().toLowerCase();

    String convertedMonth = monthMap.get(normalizedMonthName);

    if (Objects.isNull(convertedMonth)) {

      throw new BadRequestException(String.format("Mês inválido: %s!", monthName));
    }

    return convertedMonth;
  }

  @Override
  public Integer convertToIndex(String monthName) {
    String normalizedMonthName = monthName.trim().toLowerCase();

    String convertedMonth = monthMap.get(normalizedMonthName);

    if (Objects.isNull(convertedMonth)) {

      throw new BadRequestException(String.format("Mês inválido: %s!", monthName));
    }

    // Subtracts 1 to make it zero indexed
    return Month.valueOf(convertedMonth).getValue() - 1;
  }
}
