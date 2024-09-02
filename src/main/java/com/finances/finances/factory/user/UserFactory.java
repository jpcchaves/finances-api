package com.finances.finances.factory.user;

import com.finances.finances.domain.entities.Role;
import com.finances.finances.domain.entities.User;
import java.util.Set;

public interface UserFactory {

  User buildUser(String name, String email, String password, Set<Role> roles);
}
