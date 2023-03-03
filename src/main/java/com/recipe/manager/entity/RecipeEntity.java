package com.recipe.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Recipe")
@Getter
@Setter
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
  private Boolean vegetarian;

  @Column(name = "servings")
  private Integer servings;

  @ElementCollection
  @Column(name = "ingredients")
  private List<String> ingredients;

  @Column(name = "instructions")
  private String instructions;

}
