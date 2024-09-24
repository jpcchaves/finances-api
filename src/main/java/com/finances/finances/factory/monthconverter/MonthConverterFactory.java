package com.finances.finances.factory.monthconverter;

import com.finances.finances.strategy.monthconverter.MonthConverterStrategy;
import com.finances.finances.strategy.monthconverter.PortugueseToEnglishMonthConverterStrategy;
import org.springframework.stereotype.Component;

@Component
public class MonthConverterFactory {

  private final PortugueseToEnglishMonthConverterStrategy portugueseToEnglishMonthConverterStrategy;

  public MonthConverterFactory(
      PortugueseToEnglishMonthConverterStrategy portugueseToEnglishMonthConverterStrategy) {
    this.portugueseToEnglishMonthConverterStrategy = portugueseToEnglishMonthConverterStrategy;
  }

  public MonthConverterStrategy getPtBrToEnConverter() {

    return portugueseToEnglishMonthConverterStrategy;
  }
}
