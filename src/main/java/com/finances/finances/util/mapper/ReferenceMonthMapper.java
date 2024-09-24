package com.finances.finances.util.mapper;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMonthMapper {

  private static final Map<Integer, String> referenceMonthMap = new HashMap<>();

  static {
    referenceMonthMap.put(0, "Janeiro");
    referenceMonthMap.put(1, "Fevereiro");
    referenceMonthMap.put(2, "Março");
    referenceMonthMap.put(3, "Abril");
    referenceMonthMap.put(4, "Maio");
    referenceMonthMap.put(5, "Junho");
    referenceMonthMap.put(6, "Julho");
    referenceMonthMap.put(7, "Agosto");
    referenceMonthMap.put(8, "Setembro");
    referenceMonthMap.put(9, "Outubro");
    referenceMonthMap.put(10, "Novembro");
    referenceMonthMap.put(11, "Dezembro");
  }

  public String getReferenceMonthByIndex(Integer referenceMonthIndex) {

    return referenceMonthMap.get(referenceMonthIndex);
  }
}
