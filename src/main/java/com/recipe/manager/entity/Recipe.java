package com.recipe.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

  @Id
  @Column(name = "Id")
  @GenericGenerator(name = "native_generator", strategy = "native")
  @GeneratedValue(generator = "native_generator")

  private Integer id;

  @Column(name = "recipe_name")
  private String name;

  @Column(name = "is_vegetarian")
  private Boolean isVegetarian;

  @Column(name = "servings")
  private Integer servings;

  @Column(name = "ingredients")
  private String ingredients;

  @Column(name = "instructions", length = 1000)
  private String instructions;

  @Column(name = "is_deleted")
  private boolean isDeleted;

}
