package com.finances.finances.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

  @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {

    logger.error(ex.getMessage());
    logger.error(Arrays.toString(ex.getStackTrace()));

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(ex.getMessage())
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(
                "An error occurred processing your request. Tip: Verify the request body data and try again!")
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ExceptionResponseDTO> handleAccessDeniedException(
      BadRequestException ex, WebRequest request) {

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(ex.getMessage())
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionResponseDTO> handleAccessDeniedException(
      ResourceNotFoundException ex, WebRequest request) {

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(ex.getMessage())
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public final ResponseEntity<ExceptionResponseDTO> handleInternalServerErrorException(
      InternalServerErrorException ex, WebRequest request) {

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(ex.getMessage())
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    List<String> errors = new ArrayList<>();

    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String errorMessage;

              errorMessage = error.getDefaultMessage();

              errors.add(errorMessage);
            });

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(errors.get(0))
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
