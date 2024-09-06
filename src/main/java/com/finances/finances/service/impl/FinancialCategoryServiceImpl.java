package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.factory.financialcategory.FinancialCategoryFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.financialcategory.FinancialCategoryMapper;
import com.finances.finances.persistence.repository.FinancialCategoryRepository;
import com.finances.finances.service.FinancialCategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FinancialCategoryServiceImpl implements FinancialCategoryService {

  private final FinancialCategoryRepository financialCategoryRepository;
  private final FinancialCategoryMapper financialCategoryMapper;
  private final FinancialCategoryFactory financialCategoryFactory;
  private final AuthHelper authHelper;

  public FinancialCategoryServiceImpl(
      FinancialCategoryRepository financialCategoryRepository,
      FinancialCategoryMapper financialCategoryMapper,
      FinancialCategoryFactory financialCategoryFactory,
      AuthHelper authHelper) {
    this.financialCategoryRepository = financialCategoryRepository;
    this.financialCategoryMapper = financialCategoryMapper;
    this.financialCategoryFactory = financialCategoryFactory;
    this.authHelper = authHelper;
  }

  @Override
  public ResponseDTO<?> create(FinancialCategoryRequestDTO requestDTO) {

    if (financialCategoryRepository.existsByName(requestDTO.getName())) {

      throw new BadRequestException("Já existe uma categoria com o nome informado!");
    }

    FinancialCategory financialCategory =
        financialCategoryFactory.buildFinancialCategory(
            requestDTO.getName(), authHelper.getUserDetails());

    financialCategory = financialCategoryRepository.save(financialCategory);

    return ResponseDTO.withData(financialCategoryMapper.toDTO(financialCategory));
  }

  @Override
  public ResponseDTO<?> update(Long financialCategoryId, FinancialCategoryRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ResponseDTO<PaginationResponseDTO<FinancialCategoryResponseDTO>> list(Pageable pageable) {
    return null;
  }

  @Override
  public ResponseDTO<FinancialCategoryResponseDTO> findById(Long financialCategoryId) {
    return null;
  }

  @Override
  public ResponseDTO<?> delete(Long financialCategoryId) {
    return null;
  }

  public FinancialCategoryMapper getFinancialCategoryMapper() {
    return financialCategoryMapper;
  }
}
