/*
package com.recipe.manager.service;

import static com.recipe.manager.util.Constants.RECIPE_NOT_FOUND_BY_ID_ERROR_MSG;

import com.recipe.manager.entity.RecipeEntity;
import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.mapper.RecipeMapper;
import com.recipe.manager.repository.RecipeRepository;
import com.recipe.manager.server.model.Recipe;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

  private final RecipeMapper recipeMapper;
  private final RecipeRepository recipeRepository;

  public void addRecipe(Recipe recipe) {
    log.debug("adding a new recipe");
    RecipeEntity recipeEntity = recipeMapper.mapCreateRecipe(recipe);
    recipeRepository.save(recipeEntity);
  }

  public void deleteRecipe(Integer id) {
      validateAndGetRecipeFromDb(id);
      log.debug("deleting a recipe with id: {}", id);
      recipeRepository.deleteById(id);
  }

  public Recipe getRecipeById(Integer id) {
    log.debug("getting recipe by id: {}", id);
    RecipeEntity recipeEntity = validateAndGetRecipeFromDb(id);
    return recipeMapper.mapGetRecipeById(recipeEntity);
  }

  public List<Recipe> getRecipes() {
    log.debug("getting all recipes");
    return recipeRepository.findAll()
        .stream()
        .map(recipeMapper::mapGetRecipeById)
        .toList();
  }

  public void updateRecipes(Integer id, Recipe recipe) {
    RecipeEntity recipeEntity = validateAndGetRecipeFromDb(id);
    log.debug("updating a recipe with id: {}", id);
    RecipeEntity newRecipeEntity = recipeMapper.mapUpdateRecipe(recipe, recipeEntity);
    recipeRepository.save(newRecipeEntity);
  }

  private RecipeEntity validateAndGetRecipeFromDb(Integer id) {
    return recipeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(RECIPE_NOT_FOUND_BY_ID_ERROR_MSG + id));
  }
}
*/
