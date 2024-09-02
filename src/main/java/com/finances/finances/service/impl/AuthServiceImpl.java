package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.auth.LoginRequestDTO;
import com.finances.finances.domain.dto.auth.LoginResponseDTO;
import com.finances.finances.domain.dto.auth.RegisterRequestDTO;
import com.finances.finances.domain.dto.auth.UserLoginResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.entities.Role;
import com.finances.finances.domain.entities.User;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.exception.InternalServerErrorException;
import com.finances.finances.factory.user.UserFactory;
import com.finances.finances.persistence.repository.RoleRepository;
import com.finances.finances.persistence.repository.UserRepository;
import com.finances.finances.security.JwtTokenProvider;
import com.finances.finances.service.AuthService;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserFactory userFactory;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      UserFactory userFactory,
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userFactory = userFactory;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  @Transactional
  public ResponseDTO<?> register(RegisterRequestDTO requestDTO) {

    if (!Objects.equals(requestDTO.getPassword(), requestDTO.getConfirmPassword())) {

      throw new BadRequestException("As senhas não conferem!");
    }

    if (userRepository.existsByEmail(requestDTO.getEmail())) {

      throw new BadRequestException("Já existe um usuário cadastrado com o email informado!");
    }

    Role roleUser =
        roleRepository
            .findByName("ROLE_USER")
            .orElseThrow(
                () ->
                    new InternalServerErrorException(
                        "Ocorreu um erro interno. Contate o suporte!"));

    String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());

    User user =
        userFactory.buildUser(
            requestDTO.getName(), requestDTO.getEmail(), encodedPassword, Set.of(roleUser));

    userRepository.save(user);

    return ResponseDTO.withMessage("Usuário cadastrado com sucesso!");
  }

  @Override
  public ResponseDTO<LoginResponseDTO> login(LoginRequestDTO requestDTO) {

    try {

      User user =
          userRepository
              .findByEmail(requestDTO.getEmail())
              .orElseThrow(
                  () -> new BadRequestException("Usuário não encontrado com o email informado!"));

      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

      Authentication authentication = authenticationManager.authenticate(authenticationToken);

      SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = jwtTokenProvider.generateToken(authentication);

      UserLoginResponseDTO userLoginResponseDTO =
          new UserLoginResponseDTO(user.getId(), user.getName(), user.getEmail());

      LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, userLoginResponseDTO);

      return new ResponseDTO<>("Usuário autenticado com sucesso!", loginResponseDTO);

    } catch (AuthenticationException ex) {

      logger.error(ex.getMessage());

      throw new InternalServerErrorException(
          "Ocorreu um erro ao realizar a autenticação do usuário!");
    }
  }
}
