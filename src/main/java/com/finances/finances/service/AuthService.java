package com.finances.finances.service;

import com.finances.finances.domain.dto.auth.LoginRequestDTO;
import com.finances.finances.domain.dto.auth.LoginResponseDTO;
import com.finances.finances.domain.dto.auth.RegisterRequestDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;

public interface AuthService {

  ResponseDTO<?> register(RegisterRequestDTO requestDTO);

  ResponseDTO<LoginResponseDTO> login(LoginRequestDTO requestDTO);
}
