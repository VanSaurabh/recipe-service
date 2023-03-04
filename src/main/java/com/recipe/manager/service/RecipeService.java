package com.recipe.manager.service;

import static com.recipe.manager.util.Constants.RECIPES_NOT_FOUND_ERROR_MSG;
import static com.recipe.manager.util.Constants.RECIPE_NOT_FOUND_BY_ID_ERROR_MSG;

import com.recipe.manager.entity.Recipe;
import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.mapper.RecipeMapper;
import com.recipe.manager.repository.RecipeRepository;
import com.recipe.manager.repository.RecipeSearchRepository;
import com.recipe.manager.server.model.RecipeRequest;
import com.recipe.manager.server.model.RecipeResponse;
import java.util.Collections;
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
  private final RecipeSearchRepository recipeSearchRepository;

  public void addRecipe(RecipeRequest recipeRequest) {
    log.debug("adding a new recipe");
    Recipe recipe = recipeMapper.mapCreateRecipe(recipeRequest);
    recipeRepository.save(recipe);
  }

  public RecipeResponse getRecipeById(Integer id) {
    log.debug("getting recipe by id: {}", id);
    Recipe recipe = validateAndGetRecipeFromDb(id);
    if(recipe.isDeleted()) {
      throw new NotFoundException("Recipe with given id: "+ id + " not found");
    }
    return recipeMapper.mapGetRecipeById(recipe);
  }

  public List<RecipeResponse> getRecipes() {
    log.debug("getting all recipes");
    return recipeRepository.findAllByIsDeleted(false)
        .orElseThrow(() -> new NotFoundException(RECIPES_NOT_FOUND_ERROR_MSG))
        .stream()
        .map(recipeMapper::mapGetRecipeById)
        .toList();
  }

  public void updateRecipes(Integer id, RecipeRequest recipeRequest) {
    Recipe recipe = validateAndGetRecipeFromDb(id);
    log.debug("updating a recipe with id: {}", id);
    Recipe newRecipeEntity = recipeMapper.mapUpdateRecipe(recipeRequest, recipe);
    recipeRepository.save(newRecipeEntity);
  }

  public void deleteRecipe(Integer id) {
    Recipe recipe = validateAndGetRecipeFromDb(id);
    log.debug("deleting a recipe with id: {}", id);
    recipe.setDeleted(true);
    recipeRepository.save(recipe);
  }

  public List<RecipeResponse> searchRecipe(Boolean isVeg, Integer serving,
      List<String> includedIngredients, List<String> excludedIngredients,
      String searchInstructions) {
    return recipeSearchRepository
        .getRecipesBySearchCriteria(isVeg, serving, includedIngredients, excludedIngredients,
            searchInstructions)
        .orElse(Collections.emptyList())
        .stream()
        .map(recipeMapper::mapGetRecipeById)
        .toList();
  }

  private Recipe validateAndGetRecipeFromDb(Integer id) {
    return recipeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(RECIPE_NOT_FOUND_BY_ID_ERROR_MSG + id));
  }
}
