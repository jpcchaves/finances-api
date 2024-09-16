package com.finances.finances;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.finances.finances.config.AbstractTestContainerConfig;
import com.finances.finances.controller.ExpenseReportController;
import com.finances.finances.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinancesApiApplicationTests extends AbstractTestContainerConfig {

  @Autowired private UserRepository userRepository;

  @Autowired private ExpenseReportController expenseReportController;

  @Test
  void contextLoads() {

    assertNotNull(userRepository);
    assertNotNull(expenseReportController);
  }
}
