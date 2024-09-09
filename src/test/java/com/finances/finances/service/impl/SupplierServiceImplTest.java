package com.finances.finances.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;
import com.finances.finances.factory.supplier.SupplierFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.supplier.SupplierMapper;
import com.finances.finances.persistence.repository.SupplierRepository;
import java.util.Optional;
import java.util.Set;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

  private final Faker faker = new Faker();
  @Mock private SupplierRepository supplierRepository;
  @Mock private AuthHelper authHelper;
  @Mock private SupplierMapper supplierMapper;
  @Mock private SupplierFactory supplierFactory;
  @InjectMocks private SupplierServiceImpl supplierService;
  private User user;

  private Supplier supplier;

  private SupplierRequestDTO supplierRequestDTO;

  @BeforeEach
  void setup() {

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(20);

    user = new User(name, email, password, Set.of());

    supplier = new Supplier(faker.lorem().characters(20), user);

    supplierRequestDTO = new SupplierRequestDTO(supplier.getName());
  }

  @DisplayName(
      "Test given supplier when create then return success message inside ResponseDTO object")
  @Test
  void create() {

    when(supplierRepository.existsByName(supplier.getName())).thenReturn(Boolean.FALSE);

    when(supplierFactory.buildSupplier(supplier.getName(), user)).thenReturn(supplier);

    when(supplierRepository.save(supplier)).thenReturn(supplier);

    when(authHelper.getUserDetails()).thenReturn(user);

    ResponseDTO<?> responseDTO = supplierService.create(supplierRequestDTO);

    assertNotNull(responseDTO);
    assertEquals("Fornecedor cadastrado com sucesso!", responseDTO.getMessage());
  }

  @DisplayName(
      "Test given supplierId and SupplierRequestDTO when update then return success message")
  @Test
  void update() {

    when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));

    when(supplierRepository.findByName(supplier.getName())).thenReturn(Optional.empty());

    when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

    ResponseDTO<?> responseDTO = supplierService.update(anyLong(), supplierRequestDTO);

    assertNotNull(responseDTO);
    assertEquals("Fornecedor atualizado com sucesso!", responseDTO.getMessage());
  }

  @Test
  void list() {}

  @Test
  void findById() {}
}
