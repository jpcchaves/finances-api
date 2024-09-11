package com.finances.finances.service.impl;

import com.finances.finances.domain.dto.common.PaginationResponseDTO;
import com.finances.finances.domain.dto.common.ResponseDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryRequestDTO;
import com.finances.finances.domain.dto.financialcategory.FinancialCategoryResponseDTO;
import com.finances.finances.domain.entities.FinancialCategory;
import com.finances.finances.exception.BadRequestException;
import com.finances.finances.exception.ResourceNotFoundException;
import com.finances.finances.factory.financialcategory.FinancialCategoryFactory;
import com.finances.finances.helper.auth.AuthHelper;
import com.finances.finances.mapper.financialcategory.FinancialCategoryMapper;
import com.finances.finances.persistence.repository.FinancialCategoryRepository;
import com.finances.finances.service.FinancialCategoryService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public ResponseDTO<?> create(FinancialCategoryRequestDTO requestDTO) {

    if (financialCategoryRepository.existsByName(
        authHelper.getUserDetails().getId(), requestDTO.getName())) {

      throw new BadRequestException("Já existe uma categoria com o nome informado!");
    }

    FinancialCategory financialCategory =
        financialCategoryFactory.buildFinancialCategory(
            requestDTO.getName(), authHelper.getUserDetails());

    financialCategoryRepository.save(financialCategory);

    return ResponseDTO.withMessage("Categoria criada com sucesso!");
  }

  @Override
  @Transactional
  public ResponseDTO<?> update(Long financialCategoryId, FinancialCategoryRequestDTO requestDTO) {

    FinancialCategory financialCategory =
        financialCategoryRepository
            .findById(financialCategoryId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Categoria financeira não encontrada com o ID informado!"));

    if (financialCategoryRepository.existsByName(
        authHelper.getUserDetails().getId(), requestDTO.getName())) {

      throw new BadRequestException("Já existe uma categoria com o nome informado!");
    }

    financialCategory.setName(requestDTO.getName());

    financialCategoryRepository.save(financialCategory);

    return ResponseDTO.withMessage("Categoria financeira atualizada com sucesso!");
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseDTO<PaginationResponseDTO<FinancialCategoryResponseDTO>> list(Pageable pageable) {

    Page<FinancialCategory> financialCategoryPage =
        financialCategoryRepository.findAll(authHelper.getUserDetails().getId(), pageable);

    List<FinancialCategoryResponseDTO> financialCategoryResponseDTOList =
        financialCategoryMapper.toDTO(financialCategoryPage.getContent());

    PaginationResponseDTO<FinancialCategoryResponseDTO> paginationResponseDTO =
        new PaginationResponseDTO<FinancialCategoryResponseDTO>()
            .builder()
            .setContent(financialCategoryResponseDTOList)
            .setPage(financialCategoryPage.getNumber())
            .setSize(financialCategoryPage.getSize())
            .setTotalPages(financialCategoryPage.getTotalPages())
            .setTotalElements(financialCategoryPage.getTotalElements())
            .build();

    return ResponseDTO.withData(paginationResponseDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseDTO<FinancialCategoryResponseDTO> findById(Long financialCategoryId) {

    FinancialCategory financialCategory =
        financialCategoryRepository
            .findById(authHelper.getUserDetails().getId(), financialCategoryId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Categoria financeira não encontrada!"));

    FinancialCategoryResponseDTO responseDTO = financialCategoryMapper.toDTO(financialCategory);

    return ResponseDTO.withData(responseDTO);
  }

  @Override
  public ResponseDTO<?> delete(Long financialCategoryId) {
    return null;
  }
}
