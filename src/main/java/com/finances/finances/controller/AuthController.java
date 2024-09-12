package com.finances.finances.controller;

import com.finances.finances.domain.dto.auth.LoginRequestDTO;
import com.finances.finances.domain.dto.auth.LoginResponseDTO;
import com.finances.finances.domain.dto.auth.RegisterRequestDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthController {

  ResponseEntity<ResponseDTO<?>> register(RegisterRequestDTO requestDTO);

  ResponseEntity<ResponseDTO<LoginResponseDTO>> login(LoginRequestDTO requestDTO);
}
