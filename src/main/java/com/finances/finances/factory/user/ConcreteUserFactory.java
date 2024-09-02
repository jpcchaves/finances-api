package com.finances.finances.factory.user;

import com.finances.finances.domain.entities.Role;
import com.finances.finances.domain.entities.User;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ConcreteUserFactory implements UserFactory {

  @Override
  public User buildUser(String name, String email, String password, Set<Role> roles) {
    return new User(name, email, password, roles);
  }
}
