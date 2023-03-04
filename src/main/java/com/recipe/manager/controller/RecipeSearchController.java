package com.recipe.manager.controller;

import com.recipe.manager.server.model.Recipe;
import com.recipe.manager.service.RecipeSearchService;
import com.recipe.search.server.api.SearchRecipeApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeSearchController implements SearchRecipeApi {

  private final RecipeSearchService recipeSearchService;


  @Override
  public ResponseEntity<List<Recipe>> getRecipesByCategory(Boolean isVeg, Integer serving,
      List<String> includedIngredients, List<String> excludedIngredients,
      String searchInstructions) {
    return ResponseEntity.ok(recipeSearchService.searchRecipe(isVeg, serving, includedIngredients, excludedIngredients, searchInstructions));
  }
}
