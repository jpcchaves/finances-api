package com.finances.finances.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

public class UserRequestDTO implements Serializable {

  @Serial private static final long serialVersionUID = 2392338705752103328L;

  @NotBlank(message = "Nome é obrigatório!")
  private String name;

  @Email
  @NotBlank(message = "Email é obrigatório!")
  private String email;

  @NotBlank(message = "A senha é obrigatória!")
  private String password;

  @NotBlank(message = "A confirmação de senha é obrigatória!")
  private String confirmPassword;

  public UserRequestDTO() {}

  public UserRequestDTO(String name, String email, String password, String confirmPassword) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.confirmPassword = confirmPassword;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
