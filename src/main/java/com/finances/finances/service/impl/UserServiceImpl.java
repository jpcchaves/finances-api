package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.user.UserRequestDTO;
import com.finances.finances.domain.dto.user.UserResponseDTO;
import com.finances.finances.domain.entities.User;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.exception.ResourceNotFoundException;
import com.finances.finances.persistence.repository.UserRepository;
import com.finances.finances.service.UserService;
import java.util.Objects;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserResponseDTO update(Long userId, UserRequestDTO requestDTO) {

    if (!Objects.equals(requestDTO.getPassword(), requestDTO.getConfirmPassword())) {

      throw new BadRequestException("As senhas devem ser iguais!");
    }

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new BadRequestException("Usuario inexistente!"));

    if (!Objects.equals(requestDTO.getEmail(), user.getEmail())) {

      if (userRepository.existsByEmail(requestDTO.getEmail())) {

        throw new BadRequestException("Email ja cadastrado!");
      }
    }

    user.setName(requestDTO.getName());
    user.setEmail(requestDTO.getEmail());
    user.setPassword(requestDTO.getPassword());

    user = userRepository.saveAndFlush(user);

    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
  }

  @Override
  @Transactional
  public User getUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new ResourceNotFoundException("Usuário não encontrado com o email informado!"));
  }

  // Username refers to email
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return getUserByEmail(email);
  }
}
