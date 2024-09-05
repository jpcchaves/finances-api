package com.finances.finances.helper.auth;

import com.finances.finances.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelperImpl implements AuthHelper {

  @Override
  public Authentication getAuthentication() {

    return SecurityContextHolder.getContext().getAuthentication();
  }

  @Override
  public User getUserDetails() {

    return (User) getAuthentication().getPrincipal();
  }

  @Override
  public String getName() {

    return getUserDetails().getName();
  }
}
