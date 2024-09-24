package com.finances.finances.domain.entities;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "expenses")
@SequenceGenerator(name = "seq_expense", sequenceName = "seq_expense", allocationSize = 1)
public class Expense implements Serializable {

  @Serial private static final long serialVersionUID = 7801056184069866508L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_expense")
  private Long id;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false, precision = 2, scale = 10)
  private BigDecimal amount = BigDecimal.ZERO;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate dueDate;

  private String notes;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT))
  private User user;

  @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false,
      cascade = {CascadeType.DETACH})
  @JoinColumn(
      name = "financial_category_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "financial_category_fk", value = ConstraintMode.CONSTRAINT))
  private FinancialCategory category;

  @ManyToOne(
      fetch = FetchType.LAZY,
      optional = false,
      cascade = {CascadeType.DETACH})
  @JoinColumn(
      name = "supplier_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "supplier_fk", value = ConstraintMode.CONSTRAINT))
  private Supplier supplier;

  @Column(nullable = false)
  private Integer referenceMonth;

  @Column(nullable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @Temporal(TemporalType.TIMESTAMP)
  private Date deletedAt;

  public Expense() {}

  public Expense(
      Long id,
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      String notes,
      User user,
      FinancialCategory category,
      Supplier supplier,
      Date createdAt,
      Date updatedAt,
      Date deletedAt) {
    this.id = id;
    this.description = description;
    this.amount = amount;
    this.dueDate = dueDate;
    this.notes = notes;
    this.user = user;
    this.category = category;
    this.supplier = supplier;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public Expense(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      User user,
      FinancialCategory category,
      Supplier supplier,
      String notes) {
    this.description = description;
    this.amount = amount;
    this.dueDate = dueDate;
    this.user = user;
    this.category = category;
    this.supplier = supplier;
    this.notes = notes;
  }

  public Expense(
      String description,
      BigDecimal amount,
      LocalDate dueDate,
      User user,
      FinancialCategory category,
      Supplier supplier,
      String notes,
      Integer referenceMonth) {
    this.description = description;
    this.amount = amount;
    this.dueDate = dueDate;
    this.user = user;
    this.category = category;
    this.supplier = supplier;
    this.notes = notes;
    this.referenceMonth = referenceMonth;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public FinancialCategory getCategory() {
    return category;
  }

  public void setCategory(FinancialCategory category) {
    this.category = category;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public Integer getReferenceMonth() {
    return referenceMonth;
  }

  public void setReferenceMonth(Integer referenceMonth) {
    this.referenceMonth = referenceMonth;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Expense expense = (Expense) o;
    return Objects.equals(id, expense.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Expense{"
        + "id="
        + id
        + ", description='"
        + description
        + '\''
        + ", amount="
        + amount
        + ", dueDate="
        + dueDate
        + '}';
  }
}
