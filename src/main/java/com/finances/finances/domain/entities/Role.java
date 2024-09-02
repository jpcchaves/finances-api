package com.finances.finances.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "unique_role_name",
          columnNames = {"name"})
    })
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1)
public class Role implements Serializable, GrantedAuthority {

  @Serial private static final long serialVersionUID = -283943394835529005L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
  private Long id;

  @Column(unique = true, nullable = false, length = 100)
  private String name;

  public Role() {}

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Role(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Role role = (Role) o;
    return Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
  }

  @Override
  public String getAuthority() {
    return name;
  }
}
