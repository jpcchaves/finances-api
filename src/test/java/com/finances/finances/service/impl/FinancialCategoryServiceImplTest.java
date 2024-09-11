package com.finances.finances.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.domain.entities.User;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.factory.financialcategory.FinancialCategoryFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.financialcategory.FinancialCategoryMapper;
import com.finances.finances.persistence.repository.FinancialCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

@ExtendWith(MockitoExtension.class)
class FinancialCategoryServiceImplTest {

  private final Faker faker = new Faker();

  @Mock private FinancialCategoryRepository financialCategoryRepository;

  @Mock private AuthHelper authHelper;

  @Mock private FinancialCategoryMapper financialCategoryMapper;

  @Mock private FinancialCategoryFactory financialCategoryFactory;

  @InjectMocks private FinancialCategoryServiceImpl financialCategoryService;

  private User user;

  private FinancialCategory financialCategory;

  private List<FinancialCategory> financialCategoryList = new ArrayList<>();

  private List<FinancialCategoryResponseDTO> financialCategoryResponseDTOList = new ArrayList<>();

  private FinancialCategoryRequestDTO financialCategoryRequestDTO;

  private FinancialCategoryResponseDTO financialCategoryResponseDTO;

  private Page<FinancialCategory> financialCategoryPage;

  private Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

  @BeforeEach
  void setup() {

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(20);

    user = new User(name, email, password, Set.of());
    user.setId(faker.random().nextLong());

    financialCategory = new FinancialCategory(faker.lorem().characters(20), user);

    financialCategoryRequestDTO = new FinancialCategoryRequestDTO(financialCategory.getName());

    for (int i = 0; i < 10; i++) {
      financialCategoryList.add(new FinancialCategory(faker.lorem().characters(20), user));
    }

    for (FinancialCategory fc : financialCategoryList) {

      financialCategoryResponseDTOList.add(
          new FinancialCategoryResponseDTO(fc.getId(), fc.getName()));
    }

    financialCategoryResponseDTO = financialCategoryResponseDTOList.get(0);

    financialCategoryPage =
        new PageImpl<>(financialCategoryList, pageable, financialCategoryList.size());
  }

  @DisplayName("Test given financial category when create then return success message")
  @Test
  void create() {

    when(financialCategoryRepository.existsByName(financialCategory.getName()))
        .thenReturn(Boolean.FALSE);

    when(authHelper.getUserDetails()).thenReturn(user);

    when(financialCategoryFactory.buildFinancialCategory(anyString(), any(User.class)))
        .thenReturn(financialCategory);

    when(financialCategoryRepository.save(financialCategory)).thenReturn(financialCategory);

    ResponseDTO<?> responseDTO = financialCategoryService.create(financialCategoryRequestDTO);

    assertNotNull(responseDTO);
    assertNotNull(responseDTO.getMessage());
    assertNull(responseDTO.getData());
    assertEquals("Categoria criada com sucesso!", responseDTO.getMessage());
  }

  @DisplayName(
      "Test given financial category when try to create with duplicate name then return success message")
  @Test
  void createUnsuccessful() {

    when(financialCategoryRepository.existsByName(financialCategory.getName()))
        .thenReturn(Boolean.TRUE);

    BadRequestException exception =
        assertThrows(
            BadRequestException.class,
            () -> financialCategoryService.create(financialCategoryRequestDTO));

    assertEquals("Já existe uma categoria com o nome informado!", exception.getMessage());
  }

  @Test
  void update() {}

  @Test
  void list() {}

  @Test
  void findById() {}
}
