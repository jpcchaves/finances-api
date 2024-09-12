package com.finances.finances.controller.impl;

import com.finances.finances.controller.AuthController;
import com.finances.finances.domain.dto.auth.LoginRequestDTO;
import com.finances.finances.domain.dto.auth.LoginResponseDTO;
import com.finances.finances.domain.dto.auth.RegisterRequestDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {

  private final AuthService authService;

  public AuthControllerImpl(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  @Override
  public ResponseEntity<ResponseDTO<?>> register(
      @Valid @RequestBody RegisterRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(requestDTO));
  }

  @PostMapping("/login")
  @Override
  public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(
      @Valid @RequestBody LoginRequestDTO requestDTO) {

    return ResponseEntity.ok(authService.login(requestDTO));
  }
}
