package com.finances.finances.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.domain.entities.User;
import com.finances.finances.factory.supplier.SupplierFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.supplier.SupplierMapper;
import com.finances.finances.persistence.repository.SupplierRepository;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.data.domain.*;

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

  private List<Supplier> supplierList;

  private List<SupplierResponseDTO> supplierResponseDTOS = new ArrayList<>();

  private SupplierResponseDTO supplierResponseDTO;

  private Page<Supplier> supplierPage;

  private Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

  @BeforeEach
  void setup() {

    String name = faker.name().fullName();
    String email = faker.internet().emailAddress();
    String password = faker.number().digits(20);

    user = new User(name, email, password, Set.of());
    user.setId(faker.random().nextLong());

    supplier = new Supplier(faker.lorem().characters(20), user);

    supplierRequestDTO = new SupplierRequestDTO(supplier.getName());

    supplierList =
        List.of(
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user),
            new Supplier(faker.lorem().characters(20), user));

    for (Supplier sup : supplierList) {

      supplierResponseDTOS.add(new SupplierResponseDTO(faker.random().nextLong(), sup.getName()));
    }

    supplierResponseDTO = supplierResponseDTOS.get(0);

    supplierPage = new PageImpl<>(supplierList, pageable, supplierList.size());
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

  @DisplayName("Test given pageable when list then return PaginationResponseDTO")
  @Test
  void list() {

    when(authHelper.getUserDetails()).thenReturn(user);

    when(supplierRepository.findAll(anyLong(), any(Pageable.class))).thenReturn(supplierPage);

    when(supplierMapper.toDTO(supplierList)).thenReturn(supplierResponseDTOS);

    ResponseDTO<PaginationResponseDTO<SupplierResponseDTO>> responseDTO =
        supplierService.list(pageable);

    assertNotNull(responseDTO);
    assertEquals(supplierList.size(), responseDTO.getData().getTotalElements());
  }

  @DisplayName("Test given supplier id when find by id then return SupplierResponseDTO")
  @Test
  void findById() {

    when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));

    when(supplierMapper.toDTO(supplier)).thenReturn(supplierResponseDTO);

    ResponseDTO<SupplierResponseDTO> responseDTO = supplierService.findById(anyLong());

    assertNotNull(responseDTO);
    assertEquals(supplierResponseDTO.getName(), responseDTO.getData().getName());
  }
}
