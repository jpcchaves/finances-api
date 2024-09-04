package com.finances.finances.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.domain.entities.*;
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
class SupplierRepositoryTest extends AbstractTestContainerConfig {

  private static final Faker faker = new Faker();

  private final Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private SupplierRepository supplierRepository;

  private User user;
  private Role role;

  private List<Supplier> supplierList = new ArrayList<>();

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

    supplierList.addAll(
        supplierRepository.saveAll(
            List.of(
                new Supplier(faker.lorem().characters(20), user),
                new Supplier(faker.lorem().characters(20), user),
                new Supplier(faker.lorem().characters(20), user),
                new Supplier(faker.lorem().characters(20), user),
                new Supplier(faker.lorem().characters(20), user))));
  }

  @DisplayName("Test given userId when find all then return supplier list")
  @Test
  void findAll() {

    List<Supplier> suppliers = supplierRepository.findAll(user.getId());

    assertNotNull(suppliers);
    assertEquals(supplierList.size(), suppliers.size());
  }

  @DisplayName("Test given userId and pageable when find all paginated then return supplier page")
  @Test
  void testFindAll() {

    Page<Supplier> supplierPage = supplierRepository.findAll(user.getId(), pageable);

    assertNotNull(supplierPage);
    assertNotNull(supplierPage.getContent());
    assertEquals(supplierList.size(), supplierPage.getContent().size());
  }

  @DisplayName("Test given userId and supplierId when find by id then return supplier")
  @Test
  void findById() {

    Supplier supplier = supplierList.get(0);

    Supplier foundSupplier = supplierRepository.findById(user.getId(), supplier.getId()).get();

    assertNotNull(foundSupplier);
    assertEquals(supplier.getId(), foundSupplier.getId());
    assertEquals(supplier.getName(), foundSupplier.getName());
    assertEquals(supplier.getUser(), foundSupplier.getUser());
  }

  @DisplayName("Test given supplier when save then return saved supplier")
  @Test
  void save() {

    Supplier savedSupplier =
        supplierRepository.save(new Supplier(faker.lorem().characters(20), user));

    assertNotNull(savedSupplier);
    assertTrue(savedSupplier.getId() > 0);
  }

  @DisplayName("Test given supplier when update then return updated supplier")
  @Test
  void update() {

    String SUPPLIER_NAME = faker.lorem().characters(20);
    String UPDATED_SUPPLIER_NAME = faker.lorem().characters(20);

    Supplier savedSupplier = supplierRepository.save(new Supplier(SUPPLIER_NAME, user));

    savedSupplier.setName(UPDATED_SUPPLIER_NAME);

    Supplier updatedSupplier = supplierRepository.save(savedSupplier);

    assertNotNull(updatedSupplier);
    assertEquals(UPDATED_SUPPLIER_NAME, updatedSupplier.getName());
    assertEquals(savedSupplier.getUser(), updatedSupplier.getUser());
  }
}
