package com.finances.finances.strategy.monthconverter;

public interface MonthConverterStrategy {

  String convert(String monthName);

  Integer convertToIndex(String monthName);
}
