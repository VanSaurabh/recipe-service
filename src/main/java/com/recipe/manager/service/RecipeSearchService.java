package com.recipe.manager.service;

import com.recipe.manager.mapper.RecipeMapper;
import com.recipe.manager.repository.RecipeSearchRepository;
import com.recipe.manager.server.model.Recipe;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeSearchService {

  private final RecipeMapper recipeMapper;
  private final RecipeSearchRepository recipeSearchRepository;

  public List<Recipe> searchRecipe(Boolean isVeg, Integer serving, List<String> includedIngredients, List<String> excludedIngredients, String searchInstructions) {
     return recipeSearchRepository.getRecipesBySearchCriteria(isVeg, serving, includedIngredients, excludedIngredients, searchInstructions)
         .orElse(Collections.emptyList())
         .stream()
         .map(recipeMapper::mapGetRecipeById)
         .toList();
  }
}
