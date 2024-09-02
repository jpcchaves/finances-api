package com.finances.finances.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
    name = "financial_categories",
    uniqueConstraints = {@UniqueConstraint(name = "unique_category_name", columnNames = "name")})
@SequenceGenerator(
    name = "seq_financial_category",
    sequenceName = "seq_financial_category",
    allocationSize = 1)
public class FinancialCategory implements Serializable {

  @Serial private static final long serialVersionUID = -1513718610023445204L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_financial_category")
  private Long id;

  @Column(unique = true, nullable = false, length = 100)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT))
  private User user;

  public FinancialCategory() {}

  public FinancialCategory(Long id, String name, User user) {
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
    FinancialCategory that = (FinancialCategory) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "FinancialCategory{" + "name='" + name + '\'' + ", id=" + id + '}';
  }
}
