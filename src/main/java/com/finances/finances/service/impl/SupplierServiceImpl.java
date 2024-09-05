package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.supplier.SupplierRequestDTO;
import com.finances.finances.domain.dto.supplier.SupplierResponseDTO;
import com.finances.finances.domain.entities.Supplier;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.supplier.SupplierMapper;
import com.finances.finances.persistence.repository.SupplierRepository;
import com.finances.finances.service.SupplierService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierServiceImpl implements SupplierService {

  private final SupplierRepository supplierRepository;
  private final AuthHelper authHelper;

  public SupplierServiceImpl(SupplierRepository supplierRepository, AuthHelper authHelper) {
    this.supplierRepository = supplierRepository;
    this.authHelper = authHelper;
  }

  @Override
  @Transactional
  public ResponseDTO<?> create(SupplierRequestDTO requestDTO) {

    Supplier supplier = new Supplier(requestDTO.getName(), authHelper.getUserDetails());

    supplierRepository.save(supplier);

    return ResponseDTO.withMessage("Fornecedor cadastrado com sucesso!");
  }

  @Override
  @Transactional
  public ResponseDTO<?> update(Long supplierId, SupplierRequestDTO requestDTO) {

    Supplier supplier =
        supplierRepository
            .findById(supplierId)
            .orElseThrow(() -> new BadRequestException("Fornecedor não encontrado!"));

    Optional<Supplier> optionalSupplier = supplierRepository.findByName(requestDTO.getName());

    if (optionalSupplier.isPresent()) {

      throw new BadRequestException("Já existe um fornecedor com o nome informado!");
    }

    supplier.setName(requestDTO.getName());

    supplierRepository.save(supplier);

    return ResponseDTO.withMessage("Fornecedor atualizado com sucesso!");
  }

  @Override
  public ResponseDTO<PaginationResponseDTO<SupplierResponseDTO>> list(Pageable pageable) {

    Page<Supplier> suppliersPage =
        supplierRepository.findAll(authHelper.getUserDetails().getId(), pageable);

    List<SupplierResponseDTO> expenseResponseDTOS =
        SupplierMapper.toDTO(suppliersPage.getContent());

    PaginationResponseDTO<SupplierResponseDTO> paginationResponseDTO =
        new PaginationResponseDTO<SupplierResponseDTO>()
            .builder()
            .setContent(expenseResponseDTOS)
            .setPage(suppliersPage.getNumber())
            .setSize(suppliersPage.getSize())
            .setTotalPages(suppliersPage.getTotalPages())
            .setTotalElements(suppliersPage.getTotalElements())
            .build();

    return new ResponseDTO<>(paginationResponseDTO);
  }

  @Override
  public ResponseDTO<SupplierResponseDTO> findById(Long supplierId) {

    Supplier supplier =
        supplierRepository
            .findById(supplierId)
            .orElseThrow(() -> new BadRequestException("Fornecedor não encontrado!"));

    SupplierResponseDTO responseDTO = new SupplierResponseDTO(supplier.getId(), supplier.getName());

    return new ResponseDTO<>(responseDTO);
  }

  @Override
  public ResponseDTO<?> delete(Long supplierId) {
    return null;
  }
}
