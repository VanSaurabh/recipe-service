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
  public ResponseEntity<Void> addRecipes(Recipe recipe) {
    log.trace("method called: addRecipes");
    recipeService.addRecipe(recipe);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Void> deleteRecipe(Integer id) {
    log.trace("method called: deleteRecipe");
    recipeService.deleteRecipe(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  public ResponseEntity<Recipe> getRecipeById(Integer id) {
    log.trace("method called: getRecipeById");
    return ResponseEntity.ok(recipeService.getRecipeById(id));
  }

  @Override
  public ResponseEntity<List<Recipe>> getRecipes() {
    log.trace("method called: getRecipes");
    return ResponseEntity.ok(recipeService.getRecipes());
  }

  @Override
  public ResponseEntity<Void> updateRecipes(Integer id, Recipe recipe) {
    log.trace("method called: updateRecipes");
    recipeService.updateRecipes(id, recipe);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
