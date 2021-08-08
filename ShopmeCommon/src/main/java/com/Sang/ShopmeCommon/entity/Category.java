package com.Sang.ShopmeCommon.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 128, nullable =false, unique = true)
  private String name;

  @Column(length = 64, nullable =false, unique = true)
  private String alias;

  @Column(length = 128, nullable =false)
  private String image;

  private boolean enabled;

  @OneToOne
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private Set<Category> children = new HashSet<>();

  public Category() {
  }

  public Category(Integer id) {
    this.id = id;
  }

  public Category(String name) {
    this.name = name;
    this.alias = name;
    this.image = "default.png";
  }

  public Category(String name, Category parent) {
    this(name);
    this.parent = parent;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Category getParent() {
    return this.parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  public Set<Category> getChildren() {
    return this.children;
  }

  public void setChildren(Set<Category> children) {
    this.children = children;
  }
}
