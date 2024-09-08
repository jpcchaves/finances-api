package com.finances.finances.domain.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> implements Serializable {

  @Serial private static final long serialVersionUID = 4991392077958729047L;

  private String message;
  private T data;

  public ResponseDTO() {}

  public ResponseDTO(String message, T data) {
    this.message = message;
    this.data = data;
  }

  public ResponseDTO(String message) {
    this.message = message;
  }

  public ResponseDTO(T data) {
    this.data = data;
  }

  public static ResponseDTO<?> withMessage(String message) {
    return new ResponseDTO<>(message);
  }

  public static <T> ResponseDTO<T> withData(T data) {

    return new ResponseDTO<>(data);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
