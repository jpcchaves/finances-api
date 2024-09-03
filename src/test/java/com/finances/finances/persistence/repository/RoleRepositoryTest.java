package com.finances.finances.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.domain.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest extends AbstractTestContainerConfig {

  @Autowired private RoleRepository roleRepository;

  private Role role;

  @BeforeEach
  void setupEach() {

    role = new Role("ROLE_TEST");
  }

  @DisplayName("Test given role object when save then return role")
  @Test
  void save() {

    Role savedRole = roleRepository.save(role);

    assertNotNull(role);
    assertEquals(role.getName(), savedRole.getName());
  }

  @DisplayName("Test given name when find by name then return role")
  @Test
  void findByName() {

    role = roleRepository.save(role);

    Role foundRole = roleRepository.findByName(role.getName()).get();

    assertNotNull(role);
    assertEquals(role.getName(), foundRole.getName());
  }
}
