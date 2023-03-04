package com.recipe.manager.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

@Entity
@Table(name = "recipe")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {

  @Id
  @Column(name = "Id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "recipe_name")
  private String name;

  @Column(name = "is_vegetarian")
  private Boolean isVegetarian;

  @Column(name = "servings")
  private Integer servings;

  @ElementCollection
  @Column(name = "ingredients")
  private List<String> ingredients;

  @Column(name = "instructions", length = 1000)
  private String instructions;

  @Column(name = "is_deleted")
  private boolean isDeleted;

}
