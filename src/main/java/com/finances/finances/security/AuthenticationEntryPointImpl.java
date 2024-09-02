package com.finances.finances.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finances.finances.exception.ExceptionResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);

  private final ObjectMapper objectMapper;

  public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ExceptionResponseDTO exceptionResponseDTO =
        ExceptionResponseDTO.builder()
            .setMessage("Não autorizado!")
            .setDetails("Você precisa estar autenticado para acessar esse recurso!")
            .build();

    response.getWriter().write(objectMapper.writer().writeValueAsString(exceptionResponseDTO));

    logger.error(
        "Error in class: " + this.getClass().getSimpleName() + ". " + exceptionResponseDTO);
  }
}
