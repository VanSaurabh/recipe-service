package com.recipe.manager.repository;

import com.recipe.manager.entity.RecipeEntity;
import com.recipe.manager.server.model.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {

  Optional<List<RecipeEntity>> findAllByIsDeleted(boolean isDeleted);

  Optional<List<RecipeEntity>> findAllByIsVegetarian(Boolean isVeg);

  @Query(
      "select r from RecipeEntity r where "
      + "r.isVegetarian = :isVeg "
      + "or r.isDeleted = false "
      + "or r.servings = :serving "
      + "or r.instructions like %:searchInstructions%"
  )
  List<Recipe> searchRecipe(Boolean isVeg, Integer serving, String searchInstructions);
}
