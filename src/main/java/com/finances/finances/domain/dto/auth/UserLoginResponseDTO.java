package com.finances.finances.domain.dto.auth;

import java.io.Serial;
import java.io.Serializable;

public class UserLoginResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = 186861692441734343L;
  private Long id;
  private String name;
  private String email;

  public UserLoginResponseDTO() {}

  public UserLoginResponseDTO(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
