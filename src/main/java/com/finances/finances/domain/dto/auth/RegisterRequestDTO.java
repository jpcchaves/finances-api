package com.finances.finances.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class RegisterRequestDTO {

  @NotBlank(message = "O nome é obrigatório!")
  @Length(min = 3, message = "O nome é muito curto!")
  private String name;

  @NotBlank(message = "Email é obrigatório!")
  @Email(message = "Email inválido!")
  private String email;

  @NotBlank(message = "A senha é obrigatória!")
  @Length(min = 8, message = "A senha deve conter pelo menos 8 caracteres")
  private String password;

  @NotBlank(message = "A confirmaçãp de senha é obrigatória!")
  @Length(min = 8, message = "A senha deve conter pelo menos 8 caracteres")
  private String confirmPassword;

  public RegisterRequestDTO() {}

  public RegisterRequestDTO(String name, String email, String password, String confirmPassword) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public @NotBlank(message = "O nome é obrigatório!") @Length(
      min = 3,
      message = "O nome é muito curto!") String getName() {
    return name;
  }

  public void setName(
      @NotBlank(message = "O nome é obrigatório!")
          @Length(min = 3, message = "O nome é muito curto!")
          String name) {
    this.name = name;
  }

  public @NotBlank(message = "Email é obrigatório!") @Email(message = "Email inválido!") String
      getEmail() {
    return email;
  }

  public void setEmail(
      @NotBlank(message = "Email é obrigatório!") @Email(message = "Email inválido!")
          String email) {
    this.email = email;
  }

  public @NotBlank(message = "A senha é obrigatória!") @Length(
      min = 8,
      message = "A senha deve conter pelo menos 8 caracteres") String getPassword() {
    return password;
  }

  public void setPassword(
      @NotBlank(message = "A senha é obrigatória!")
          @Length(min = 8, message = "A senha deve conter pelo menos 8 caracteres")
          String password) {
    this.password = password;
  }

  public @NotBlank(message = "A confirmaçãp de senha é obrigatória!") @Length(
      min = 8,
      message = "A senha deve conter pelo menos 8 caracteres") String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(
      @NotBlank(message = "A confirmaçãp de senha é obrigatória!")
          @Length(min = 8, message = "A senha deve conter pelo menos 8 caracteres")
          String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
