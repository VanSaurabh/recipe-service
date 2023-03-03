package com.recipe.manager.controller;

import com.recipe.manager.server.api.RecipeApi;
import com.recipe.manager.server.model.Recipe;
import com.recipe.manager.service.RecipeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeController implements RecipeApi {

  private final RecipeService recipeService;

  @Override
  public ResponseEntity<Recipe> createRecipes(Recipe recipe) {
    log.trace("method called: createRecipes");
    recipeService.createRecipes(recipe);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> deleteRecipe(Integer id) {
    log.trace("method called: deleteRecipe");
    return null;
  }

  @Override
  public ResponseEntity<Recipe> getRecipeById(Integer id) {
    log.trace("method called: getRecipeById");
    return null;
  }

  @Override
  public ResponseEntity<List<Recipe>> getRecipes() {
    log.trace("method called: getRecipes");
    return null;
  }

  @Override
  public ResponseEntity<Recipe> updateRecipes(Integer id, Recipe recipe) {
    log.trace("method called: updateRecipes");
    return null;
  }
}
