package com.recipe.manager.service;

import static com.recipe.manager.util.TestUtil.getRecipe;
import static com.recipe.manager.util.TestUtil.getRecipe2;
import static com.recipe.manager.util.TestUtil.getRecipeRequest;
import static com.recipe.manager.util.TestUtil.getRecipeResponse;
import static com.recipe.manager.util.TestUtil.getRecipeResponse2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.recipe.manager.entity.Recipe;
import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.mapper.RecipeMapper;
import com.recipe.manager.repository.RecipeRepository;
import com.recipe.manager.repository.RecipeSearchRepository;
import com.recipe.manager.server.model.RecipeRequest;
import com.recipe.manager.server.model.RecipeResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Recipe service test class")
class RecipeServiceTest {

  @InjectMocks
  private RecipeService recipeService;

  @Mock
  private RecipeMapper recipeMapper;
  @Mock
  private RecipeRepository recipeRepository;
  @Mock
  private RecipeSearchRepository recipeSearchRepository;

  @Test
  @DisplayName("when a new recipe is passed, then save the recipe in db")
  void shouldAddRecipe() {
    when(recipeMapper.mapCreateRecipe(any()))
        .thenReturn(new Recipe());
    recipeService.addRecipe(getRecipeRequest());

    verify(recipeMapper, times(1))
        .mapCreateRecipe(any());
    verify(recipeRepository, times(1))
        .save(any());
  }

  @Test
  @DisplayName("when a valid id is passed, then return the respective recipe")
  void shouldGetRecipeById() {
    RecipeResponse recipeResponse = getRecipeResponse();
    when(recipeRepository.findById(any()))
        .thenReturn(Optional.of(getRecipe()));
    when(recipeMapper.mapGetRecipeById(any()))
        .thenReturn(recipeResponse);

    RecipeResponse response = recipeService.getRecipeById(1);
    assertThat(response).isNotNull().isSameAs(recipeResponse);
  }

  @Test
  @DisplayName("when a invalid id is passed, then not found exception is thrown")
  void shouldThrowNotFoundWhenInvalidIdIsPassedToGetRecipeById() {
    when(recipeRepository.findById(any()))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> recipeService.getRecipeById(1));
  }

  @Test
  @DisplayName("when a deleted recipe's id is passed, then not found exception is thrown")
  void shouldThrowNotFoundExceptionWhenDeletedRecipesIdIsPassedToGetRecipeById() {
    Recipe recipe = getRecipe();
    recipe.setDeleted(true);

    when(recipeRepository.findById(any()))
        .thenReturn(Optional.of(recipe));

    assertThrows(NotFoundException.class,
        () -> recipeService.getRecipeById(1));
  }

  @Test
  @DisplayName("when all the recipes are deleted and user queries get recipes, then not found exception is thrown")
  void shouldThrowNotFoundExceptionWhenAllTheRecipesAreDeletedForGetRecipes() {
    when(recipeRepository.findAllByIsDeleted(false))
        .thenReturn(Optional.empty());
    assertThrows(NotFoundException.class,
        () -> recipeService.getRecipes());
  }

  @Test
  @DisplayName("when user queries all recipes, then list of all valid recipes are returned")
  void shouldReturnAllValidRecipes() {
    Recipe recipe1 = getRecipe();
    Recipe recipe2 = getRecipe2();

    RecipeResponse recipeResponse1 = getRecipeResponse();
    RecipeResponse recipeResponse2 = getRecipeResponse2();

    when(recipeRepository.findAllByIsDeleted(false))
        .thenReturn(Optional.of(List.of(recipe1, recipe2)));
    when(recipeMapper.mapGetRecipeById(recipe1))
        .thenReturn(recipeResponse1);
    when(recipeMapper.mapGetRecipeById(recipe2))
        .thenReturn(recipeResponse2);

    List<RecipeResponse> recipeResponses = recipeService.getRecipes();
    assertThat(recipeResponses)
        .isNotNull()
        .isNotEmpty()
        .contains(recipeResponse1, recipeResponse2);
  }

  @Test
  @DisplayName("when user wants to update a recipe, then the particular recipe should be updated")
  void shouldUpdateRecipes() {
    RecipeRequest recipeRequest = getRecipeRequest()
        .ingredients("Rice, chicken, Masala, ghee")
        .servings(5);

    when(recipeRepository.findById(any()))
        .thenReturn(Optional.of(getRecipe()));

    recipeService.updateRecipes(1, recipeRequest);

    verify(recipeMapper, times(1)).mapUpdateRecipe(any(), any());
    verify(recipeRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("when user wants to update a recipe but passed an invalid id, then not found exception is thrown")
  void shouldThrowNotFoundExceptionWhenInvalidIdIsPassedToUpdateRecipe() {
    RecipeRequest recipeRequest = getRecipeRequest();
    when(recipeRepository.findById(any()))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> recipeService.updateRecipes(1, recipeRequest));
  }

  @Test
  @DisplayName("when user wants to delete a recipe, then recipe is deleted successfully")
  void shouldDeleteRecipe() {
    when(recipeRepository.findById(any()))
        .thenReturn(Optional.of(getRecipe()));

    recipeService.deleteRecipe(1);
    verify(recipeRepository, times(1)).findById(any());
    verify(recipeRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("when user wants to search recipes by different criteria, then list of recipes are returned")
  void shouldSearchRecipe() {
    Recipe recipe1 = getRecipe();
    Recipe recipe2 = getRecipe2();

    RecipeResponse recipeResponse1 = getRecipeResponse();
    RecipeResponse recipeResponse2 = getRecipeResponse2();

    when(recipeMapper.mapGetRecipeById(recipe1))
        .thenReturn(recipeResponse1);
    when(recipeMapper.mapGetRecipeById(recipe2))
        .thenReturn(recipeResponse2);
    when(recipeSearchRepository.getRecipesBySearchCriteria(any(), any(), any(), any(), any()))
        .thenReturn(Optional.of(List.of(recipe1, recipe2)));

    List<RecipeResponse> recipeResponses = recipeService
        .searchRecipe(false, 4, List.of("Rice, chicken"),
        List.of("tomato, lentils"), "Cook");

    assertThat(recipeResponses)
        .isNotNull()
        .isNotEmpty()
        .contains(recipeResponse1, recipeResponse2);
  }

  @Test
  @DisplayName("when user wants to search recipes by different criteria and no valid recipe is found,"
      + " then empty list is returned")
  void shouldReturnEmptyListForSearchRecipe() {
    when(recipeSearchRepository.getRecipesBySearchCriteria(any(), any(), any(), any(), any()))
        .thenReturn(Optional.empty());

    List<RecipeResponse> recipeResponses = recipeService
        .searchRecipe(false, 4, List.of("Rice, chicken"),
            List.of("tomato, lentils"), "Cook");

    assertThat(recipeResponses)
        .isNotNull()
        .isEmpty();
  }
}
