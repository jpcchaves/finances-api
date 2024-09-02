package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

  @Query(value = "SELECT * FROM suppliers WHERE user_id = :userId", nativeQuery = true)
  Page<Supplier> findAll(Long userId, Pageable pageable);

  @Query(value = "SELECT * FROM suppliers WHERE user_id = :userId", nativeQuery = true)
  List<Supplier> findAll(Long userId);
}
