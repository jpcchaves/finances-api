package com.finances.finances.controller;

import com.finances.finances.domain.dto.auth.LoginRequestDTO;
import com.finances.finances.domain.dto.auth.LoginResponseDTO;
import com.finances.finances.domain.dto.auth.RegisterRequestDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth Controller", description = "Controller to manage auth routes")
public interface AuthController {

  @Operation(
      summary = "Register an user",
      description = "Register an user using the JSON representation of the registration",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> register(RegisterRequestDTO requestDTO);

  @Operation(
      summary = "Authenticates an user",
      description = "Authenticate an user using the provided username/email and password",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<LoginResponseDTO>> login(LoginRequestDTO requestDTO);
}
