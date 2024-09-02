package com.finances.finances.exception;

import java.io.Serial;

public class InternalServerErrorException extends AbstractBaseException {

  @Serial private static final long serialVersionUID = 5979944622353705529L;

  public InternalServerErrorException(String message) {
    super(message);
  }
}
