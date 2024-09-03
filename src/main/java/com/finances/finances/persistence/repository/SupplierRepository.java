package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.Supplier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

  @Query(value = "SELECT * FROM suppliers WHERE user_id = :userId", nativeQuery = true)
  Page<Supplier> findAll(Long userId, Pageable pageable);

  @Query(value = "SELECT * FROM suppliers WHERE user_id = :userId", nativeQuery = true)
  List<Supplier> findAll(Long userId);

  @Query(
      value = "SELECT * FROM suppliers WHERE user_id = :userId AND id = :supplierId",
      nativeQuery = true)
  Optional<Supplier> findById(Long userId, Long supplierId);
}
