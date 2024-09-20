package com.finances.finances.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StringUtilsHelper extends StringUtils {

  public boolean containsIgnoreCase(String str1, String str2) {

    return str1.toLowerCase().contains(str2.toLowerCase());
  }
}
