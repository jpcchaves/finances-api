package com.finances.finances.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.domain.entities.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
class ExpenseRepositoryTest extends AbstractTestContainerConfig {

  private static final Faker faker = new Faker();

  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private ExpenseRepository expenseRepository;
  @Autowired private SupplierRepository supplierRepository;
  @Autowired private FinancialCategoryRepository financialCategoryRepository;

  private User user;
  private Role role;
  private List<Expense> expensesList;
  private Expense expense;
  private Supplier supplier;
  private FinancialCategory financialCategory;

  private Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "created_at"));
  ;

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

    supplier = supplierRepository.save(new Supplier(faker.company().name(), user));

    financialCategory =
        financialCategoryRepository.save(new FinancialCategory(faker.lorem().characters(10), user));

    expensesList =
        List.of(
            new Expense(
                faker.lorem().characters(20),
                BigDecimal.ZERO,
                LocalDate.now().plusMonths(1),
                user,
                financialCategory,
                supplier,
                faker.lorem().characters(20)),
            new Expense(
                faker.lorem().characters(20),
                BigDecimal.ZERO,
                LocalDate.now().plusMonths(1),
                user,
                financialCategory,
                supplier,
                faker.lorem().characters(20)),
            new Expense(
                faker.lorem().characters(20),
                BigDecimal.ZERO,
                LocalDate.now().plusMonths(1),
                user,
                financialCategory,
                supplier,
                faker.lorem().characters(20)),
            new Expense(
                faker.lorem().characters(20),
                BigDecimal.ZERO,
                LocalDate.now().plusMonths(1),
                user,
                financialCategory,
                supplier,
                faker.lorem().characters(20)),
            new Expense(
                faker.lorem().characters(20),
                BigDecimal.ZERO,
                LocalDate.now().plusMonths(1),
                user,
                financialCategory,
                supplier,
                faker.lorem().characters(20)));

    expenseRepository.saveAll(expensesList);
  }

  @DisplayName("Test find all paginated when list then return page of expenses")
  @Test
  void findAllPaginated() {

    Page<Expense> expensePage = expenseRepository.findAll(user.getId(), pageable);

    assertNotNull(expensePage);
    assertNotNull(expensePage.getContent());
    assertEquals(expensesList.size(), expensePage.getTotalElements());
  }

  @DisplayName("Test find all when list then return list of expenses")
  @Test
  void testFindAll() {

    List<Expense> expenseList = expenseRepository.findAll(user.getId());

    assertNotNull(expenseList);
    assertEquals(expensesList.size(), expenseList.size());
  }

  @DisplayName("Test given expense id when find by id then return expense")
  @Test
  void findById() {

    List<Expense> expensesList = expenseRepository.findAll(user.getId());

    Expense expenseToFind = expensesList.get(0);

    Long expenseId = expenseToFind.getId();

    Expense foundExpense = expenseRepository.findById(user.getId(), expenseId).get();

    assertNotNull(foundExpense);

    assertEquals(expenseId, foundExpense.getId());
    assertEquals(expenseToFind.getDescription(), foundExpense.getDescription());
    assertEquals(expenseToFind.getAmount(), foundExpense.getAmount());
    assertEquals(expenseToFind.getDueDate(), foundExpense.getDueDate());
  }

  @DisplayName("Test given expense when save then return expense")
  @Test
  void save() {

    String expenseDescription = faker.lorem().characters(20);
    LocalDate dueDate = LocalDate.now().plusMonths(1);
    String notes = faker.lorem().characters(20);

    expense =
        new Expense(
            expenseDescription, BigDecimal.ZERO, dueDate, user, financialCategory, supplier, notes);

    expense = expenseRepository.save(expense);

    assertNotNull(expense);
    assertTrue(expense.getId() > 0);
    assertEquals(expenseDescription, expense.getDescription());
    assertEquals(dueDate, expense.getDueDate());
    assertEquals(notes, expense.getNotes());
    assertEquals(BigDecimal.ZERO, expense.getAmount());
  }
}
