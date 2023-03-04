package com.recipe.manager.service;

import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.mapper.RecipeMapper;
import com.recipe.manager.repository.RecipeRepository;
import com.recipe.manager.server.model.Recipe;
import com.recipe.manager.util.Constants;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeSearchService {

  private final RecipeMapper recipeMapper;
  private final RecipeRepository recipeRepository;

  public List<Recipe> searchRecipeByCategory(Boolean isVeg) {
    return recipeRepository.findAllByIsVegetarian(isVeg)
        .orElseThrow(() -> new NotFoundException(Constants.RECIPE_NOT_FOUND_ERROR_MSG))
        .stream()
        .map(recipeMapper::mapGetRecipeById)
        .toList();

  }

  public List<Recipe> searchRecipe(Boolean isVeg, Integer serving, List<String> includedIngredients, List<String> excludedIngredients, String searchInstructions) {
    return recipeRepository.searchRecipe(isVeg, serving, searchInstructions);
  }
}
