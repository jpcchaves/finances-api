package com.finances.finances.helper.auth;

import com.finances.finances.domain.entities.User;
import org.springframework.security.core.Authentication;

public interface AuthHelper {

  User getUserDetails();

  String getName();

  Authentication getAuthentication();
}
