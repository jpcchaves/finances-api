package com.finances.finances.exception;

import java.io.Serial;

public abstract class AbstractBaseException extends RuntimeException {

  @Serial private static final long serialVersionUID = 2157938126618071194L;

  public AbstractBaseException(String message) {
    super(message);
  }
}
