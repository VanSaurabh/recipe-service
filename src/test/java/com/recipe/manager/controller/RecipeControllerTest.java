package com.recipe.manager.controller;

import static com.recipe.manager.util.TestUtil.getRecipeRequest;
import static com.recipe.manager.util.TestUtil.getRecipeResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.recipe.manager.server.model.RecipeResponse;
import com.recipe.manager.service.RecipeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

  @InjectMocks
  private RecipeController recipeController;
  @Mock
  private RecipeService recipeService;

  @Test
  void shouldAddRecipes() {
    ResponseEntity<Void> response = recipeController.addRecipes(getRecipeRequest());
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(CREATED);
  }

  @Test
  void shouldDeleteRecipe() {
    ResponseEntity<Void> response = recipeController.deleteRecipe(1);
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
  }

  @Test
  void shouldGetRecipeById() {
    RecipeResponse recipeResponse = getRecipeResponse();
    when(recipeService.getRecipeById(any()))
        .thenReturn(recipeResponse);
    ResponseEntity<RecipeResponse> response = recipeController.getRecipeById(1);
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).isEqualTo(recipeResponse);
  }

  @Test
  void shouldGetRecipes() {
    RecipeResponse recipeResponse = getRecipeResponse();
    when(recipeService.getRecipes())
        .thenReturn(List.of(recipeResponse));
    ResponseEntity<List<RecipeResponse>> response = recipeController.getRecipes();
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).contains(recipeResponse);
  }

  @Test
  void shouldSearchRecipes() {
    RecipeResponse recipeResponse = getRecipeResponse();
    when(recipeService.searchRecipe(any(), any(), any(), any(), any()))
        .thenReturn(List.of(recipeResponse));
    ResponseEntity<List<RecipeResponse>> response = recipeController
        .searchRecipes(any(), any(), any(), any(), any());
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).contains(recipeResponse);
  }

  @Test
  void shouldUpdateRecipes() {
    ResponseEntity<Void> response = recipeController
        .updateRecipes(any(), any());
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(OK);
  }
}
