package com.finances.finances.service;

import com.finances.finances.domain.dto.user.UserRequestDTO;
import com.finances.finances.domain.dto.user.UserResponseDTO;
import com.finances.finances.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserResponseDTO update(Long userId, UserRequestDTO requestDTO);

  User getUserByEmail(String email);
}
