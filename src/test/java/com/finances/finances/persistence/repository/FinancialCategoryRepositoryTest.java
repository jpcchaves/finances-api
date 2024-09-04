package com.finances.finances.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.Role;
import com.finances.finances.domain.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FinancialCategoryRepositoryTest extends AbstractTestContainerConfig {

  private static final Faker faker = new Faker();

  private final Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private FinancialCategoryRepository financialCategoryRepository;

  private User user;
  private List<FinancialCategory> financialCategoryList = new ArrayList<>();
  private Role role;

  @BeforeEach
  void setupEach() {

    role = roleRepository.save(new Role("ROLE_TEST"));

    user =
        userRepository.save(
            new User(
                faker.name().name(),
                faker.internet().emailAddress(),
                faker.number().digits(20),
                Set.of(role)));

    financialCategoryList.addAll(
        List.of(
            new FinancialCategory(faker.lorem().characters(5), user),
            new FinancialCategory(faker.lorem().characters(5), user),
            new FinancialCategory(faker.lorem().characters(5), user),
            new FinancialCategory(faker.lorem().characters(5), user),
            new FinancialCategory(faker.lorem().characters(5), user)));

    financialCategoryRepository.saveAll(financialCategoryList);
  }

  @DisplayName("Test given userId when list then return list of financial categories")
  @Test
  void testFindAll() {

    List<FinancialCategory> financialCategories = financialCategoryRepository.findAll(user.getId());

    assertNotNull(financialCategories);
    assertEquals(financialCategoryList.size(), financialCategories.size());
  }

  @DisplayName("Test given userId when list then return page of financial categories")
  @Test
  void testFindAllPaginated() {

    Page<FinancialCategory> financialCategoryPage =
        financialCategoryRepository.findAll(user.getId(), pageable);

    assertNotNull(financialCategoryPage);
    assertNotNull(financialCategoryPage.getContent());
    assertEquals(financialCategoryList.size(), financialCategoryPage.getContent().size());
  }

  @DisplayName(
      "Test given userId and financialCategoryId when find by id then return financial category")
  @Test
  void findById() {

    FinancialCategory financialCategoryToFind = financialCategoryList.get(0);

    FinancialCategory foundFinancialCategory =
        financialCategoryRepository.findById(user.getId(), financialCategoryToFind.getId()).get();

    assertNotNull(foundFinancialCategory);
    assertEquals(financialCategoryToFind.getId(), foundFinancialCategory.getId());
    assertEquals(financialCategoryToFind.getName(), foundFinancialCategory.getName());
    assertEquals(financialCategoryToFind.getUser(), foundFinancialCategory.getUser());
  }

  @DisplayName("Test given financial category when save then return saved financial category")
  @Test
  void save() {

    FinancialCategory savedFinancialCategory =
        financialCategoryRepository.save(new FinancialCategory(faker.lorem().characters(20), user));

    assertNotNull(savedFinancialCategory);
  }

  @DisplayName("Test given saved finance category when update")
  @Test
  void update() {

    String FINANCIAL_CATEGORY_NAME = faker.lorem().characters(20);
    String UPDATED_FINANCIAL_CATEGORY_NAME = faker.lorem().characters(20);

    FinancialCategory savedFinancialCategory =
        financialCategoryRepository.save(new FinancialCategory(FINANCIAL_CATEGORY_NAME, user));

    savedFinancialCategory.setName(UPDATED_FINANCIAL_CATEGORY_NAME);

    FinancialCategory updatedFinancialCategory =
        financialCategoryRepository.save(savedFinancialCategory);

    assertNotNull(savedFinancialCategory);
    assertEquals(UPDATED_FINANCIAL_CATEGORY_NAME, updatedFinancialCategory.getName());
  }
}
