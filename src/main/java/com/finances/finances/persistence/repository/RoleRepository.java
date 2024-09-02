package com.finances.finances.persistence.repository;

import com.finances.finances.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query(value = "SELECT * FROM roles r WHERE UPPER(r.name) = UPPER(:name)", nativeQuery = true)
  Optional<Role> findByName(String name);
}
