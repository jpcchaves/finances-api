package com.finances.finances.domain.dto.auth;

import java.io.Serial;
import java.io.Serializable;

public class LoginResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = 2863397958120781211L;

  private String accessToken;
  private UserLoginResponseDTO user;

  public LoginResponseDTO() {}

  public LoginResponseDTO(String accessToken, UserLoginResponseDTO user) {
    this.accessToken = accessToken;
    this.user = user;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public UserLoginResponseDTO getUser() {
    return user;
  }

  public void setUser(UserLoginResponseDTO user) {
    this.user = user;
  }
}
