package com.finances.finances.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.domain.dto.common.ExpenseGroupedByCategoryDTO;
import com.finances.finances.domain.dto.common.ExpenseGroupedBySupplierDTO;
import com.finances.finances.domain.entities.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
class ExpenseRepositoryTest extends AbstractTestContainerConfig {

  private static final Faker faker = new Faker();

  private final Pageable pageable =
      PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "created_at"));

  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private ExpenseRepository expenseRepository;
  @Autowired private SupplierRepository supplierRepository;
  @Autowired private FinancialCategoryRepository financialCategoryRepository;

  private User user;
  private Role role;
  private List<Expense> expensesList = new ArrayList<>();
  private Expense expense;
  private Supplier supplier;
  private Supplier supplier2;
  private Supplier supplier3;
  private FinancialCategory financialCategory;
  private FinancialCategory financialCategory2;
  private FinancialCategory financialCategory3;

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

    supplier2 = supplierRepository.save(new Supplier(faker.company().name(), user));

    supplier3 = supplierRepository.save(new Supplier(faker.company().name(), user));

    financialCategory =
        financialCategoryRepository.save(new FinancialCategory(faker.lorem().characters(20), user));

    financialCategory2 =
        financialCategoryRepository.save(new FinancialCategory(faker.lorem().characters(20), user));

    financialCategory3 =
        financialCategoryRepository.save(new FinancialCategory(faker.lorem().characters(20), user));

    List<FinancialCategory> financialCategoriesList =
        List.of(financialCategory, financialCategory2, financialCategory3);

    List<Supplier> supplierList = List.of(supplier, supplier2, supplier3);

    for (int i = 0; i < 300; i++) {

      LocalDate dueDate = LocalDate.now().plusMonths(faker.number().numberBetween(0, 11));

      expensesList.add(
          new Expense(
              faker.lorem().characters(20),
              BigDecimal.valueOf(faker.number().randomDouble(2, 20, 500)),
              dueDate,
              user,
              financialCategoriesList.get(faker.random().nextInt(0, 2)),
              supplierList.get(faker.random().nextInt(0, 2)),
              faker.lorem().characters(20),
              dueDate.getMonthValue()));
    }

    expenseRepository.saveAll(expensesList);
  }

  @DisplayName("Test find all paginated when list then return page of expenses")
  @Test
  void findAllPaginated() {

    Page<Expense> expensePage = expenseRepository.findAll(user.getId(), pageable);

    assertNotNull(expensePage);
    assertNotNull(expensePage.getContent());
  }

  @DisplayName("Test find all when list then return list of expenses")
  @Test
  void testFindAll() {

    List<Expense> expenseList = expenseRepository.findAll(user.getId());

    assertNotNull(expenseList);
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
  }

  @DisplayName("Test given expense when update then return updated expense")
  @Test
  void update() {

    String UPDATED_DESCRIPTION = faker.lorem().characters(20);
    LocalDate UPDATED_DUE_DATE = LocalDate.now().plusMonths(1).plusDays(2);
    BigDecimal UPDATED_AMOUNT = BigDecimal.valueOf(200);

    String expenseDescription = faker.lorem().characters(20);
    LocalDate dueDate = LocalDate.now().plusMonths(1);
    String notes = faker.lorem().characters(20);

    expense =
        new Expense(
            expenseDescription, BigDecimal.ZERO, dueDate, user, financialCategory, supplier, notes);

    expense = expenseRepository.save(expense);

    expense.setDescription(UPDATED_DESCRIPTION);
    expense.setDueDate(UPDATED_DUE_DATE);
    expense.setAmount(UPDATED_AMOUNT);

    Expense updatedExpense = expenseRepository.save(expense);

    assertNotNull(updatedExpense);
    assertEquals(updatedExpense.getId(), expense.getId());
    assertEquals(UPDATED_DESCRIPTION, updatedExpense.getDescription());
    assertEquals(UPDATED_DUE_DATE, updatedExpense.getDueDate());
    assertEquals(UPDATED_AMOUNT, updatedExpense.getAmount());
  }

  @DisplayName(
      "Test given user id and range of dates when find total amount by category then return grouped expenses total amount by category")
  @Test
  void findTotalAmountByCategory() {

    List<Object[]> expenseGrouped =
        expenseRepository.findTotalAmountByAllCategories(
            user.getId(), LocalDate.now().minusMonths(3), LocalDate.now().plusMonths(1));

    List<ExpenseGroupedByCategoryDTO> expenseGroupedByCategoryDTOS =
        expenseGrouped.stream()
            .map(
                exGrouped ->
                    new ExpenseGroupedByCategoryDTO(
                        (String) exGrouped[0], (BigDecimal) exGrouped[1]))
            .toList();

    assertNotNull(expenseGroupedByCategoryDTOS);

    for (ExpenseGroupedByCategoryDTO expenseGroupedByCategoryDTO : expenseGroupedByCategoryDTOS) {

      assertNotNull(expenseGroupedByCategoryDTO.getAmount());
      assertNotNull(expenseGroupedByCategoryDTO.getCategory());
    }
  }

  @DisplayName(
      "Test given user id and range of dates when find total amount by supplier then return grouped expenses total amount by supplier")
  @Test
  void findTotalAmountBySuppliers() {

    List<Object[]> expenseGroupedBySupplier =
        expenseRepository.findTotalAmountByAllSuppliers(
            user.getId(), LocalDate.now().minusMonths(3), LocalDate.now().plusMonths(1));

    List<ExpenseGroupedBySupplierDTO> expenseGroupedBySupplierDTOs =
        expenseGroupedBySupplier.stream()
            .map(
                exGrouped ->
                    new ExpenseGroupedBySupplierDTO(
                        (String) exGrouped[0], (BigDecimal) exGrouped[1]))
            .toList();

    assertNotNull(expenseGroupedBySupplierDTOs);

    for (ExpenseGroupedBySupplierDTO expenseGroupedBySupplierDTO : expenseGroupedBySupplierDTOs) {

      assertNotNull(expenseGroupedBySupplierDTO.getAmount());
      assertNotNull(expenseGroupedBySupplierDTO.getSupplier());
    }
  }

  @DisplayName(
      "Test given userId and financialCategoryId when find total amount by category then return expense grouped by category")
  @Test
  void findTotalAmountByCategoryId() {

    List<Object[]> expenseGroupedByCategory =
        expenseRepository.findTotalAmountByCategory(
            user.getId(),
            financialCategory.getId(),
            LocalDate.now().minusMonths(3),
            LocalDate.now().plusMonths(1));

    Object[] result = expenseGroupedByCategory.get(0);

    String categoryName = (String) result[0];
    BigDecimal totalAmount = (BigDecimal) result[1];

    ExpenseGroupedByCategoryDTO expenseGroupedByCategoryDTO =
        new ExpenseGroupedByCategoryDTO(categoryName, totalAmount);

    assertNotNull(expenseGroupedByCategoryDTO);
    assertNotNull(expenseGroupedByCategoryDTO.getCategory());
    assertNotNull(expenseGroupedByCategoryDTO.getAmount());
  }

  @DisplayName(
      "Test given userId and supplierId when find total amount by supplier then return expense grouped by supplier")
  @Test
  void findTotalAmountBySupplier() {

    List<Object[]> expenseGroupedBySupplier =
        expenseRepository.findTotalAmountBySupplier(
            user.getId(),
            supplier.getId(),
            LocalDate.now().minusMonths(3),
            LocalDate.now().plusMonths(1));

    Object[] result = expenseGroupedBySupplier.get(0);

    String supplierName = (String) result[0];
    BigDecimal totalAmount = (BigDecimal) result[1];

    ExpenseGroupedBySupplierDTO expenseGroupedBySupplierDTO =
        new ExpenseGroupedBySupplierDTO(supplierName, totalAmount);

    assertNotNull(expenseGroupedBySupplierDTO);
    assertNotNull(expenseGroupedBySupplierDTO.getSupplier());
    assertNotNull(expenseGroupedBySupplierDTO.getAmount());
  }
}
