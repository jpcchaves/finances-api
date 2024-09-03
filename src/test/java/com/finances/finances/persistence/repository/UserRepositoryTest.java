package com.finances.finances.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.domain.entities.Role;
import com.finances.finances.domain.entities.User;
import java.util.Set;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractTestContainerConfig {

  private static final Faker faker = new Faker();
  private static final String USER_EMAIL = faker.internet().emailAddress();
  private static final String USER_NAME = faker.name().name();
  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  private User user;
  private Role role;

  @BeforeEach
  void setupEach() {

    role = roleRepository.save(new Role("ROLE_TEST"));

    user = new User(USER_NAME, USER_EMAIL, faker.number().digits(20), Set.of(role));
  }

  @DisplayName("Test given user when save then return user")
  @Test
  void save() {

    user = userRepository.save(user);

    assertNotNull(user);
    assertTrue(user.getId() > 0);
    assertEquals(USER_NAME, user.getName());
    assertEquals(USER_EMAIL, user.getEmail());
  }

  @DisplayName("Test given email when find by email then return user")
  @Test
  void findByEmail() {

    user = userRepository.save(user);

    User fetchedUser = userRepository.findByEmail(user.getEmail()).get();

    assertNotNull(fetchedUser);
    assertEquals(USER_NAME, fetchedUser.getName());
    assertEquals(USER_EMAIL, fetchedUser.getEmail());
  }

  @DisplayName("Test given email when exists by email then return user")
  @Test
  void existsByEmail() {

    user = userRepository.save(user);

    Boolean exists = userRepository.existsByEmail(user.getEmail());

    assertTrue(exists);
  }
}
