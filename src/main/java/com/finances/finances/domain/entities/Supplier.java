package com.finances.finances.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
    name = "suppliers",
    uniqueConstraints = {@UniqueConstraint(name = "unique_supplier_name", columnNames = "name")})
@SequenceGenerator(name = "seq_supplier", sequenceName = "seq_supplier", allocationSize = 1)
public class Supplier implements Serializable {

  @Serial private static final long serialVersionUID = -1438991438455520112L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_supplier")
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT))
  private User user;

  public Supplier() {}

  public Supplier(Long id, String name, User user) {
    this.id = id;
    this.name = name;
    this.user = user;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Supplier supplier = (Supplier) o;
    return Objects.equals(id, supplier.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Supplier{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
