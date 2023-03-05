package com.recipe.manager.mapper;

import static com.recipe.manager.util.TestUtil.getRecipe;
import static com.recipe.manager.util.TestUtil.getRecipeRequest;
import static org.assertj.core.api.Assertions.assertThat;

import com.recipe.manager.entity.Recipe;
import com.recipe.manager.server.model.RecipeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeMapperTest {

  @InjectMocks
  private RecipeMapperImpl recipeMapper;

  private static final String NAME = "name";
  private static final String IS_VEG = "isVegetarian";
  private static final String SERVINGS = "servings";
  private static final String INGREDIENTS = "ingredients";
  private static final String INSTRUCTIONS = "instructions";

  @Test
  @DisplayName("when RecipeRequest is passed, then it should be mapped to Recipe object")
  void shouldMapCreateRecipe() {
    Recipe recipe = recipeMapper.mapCreateRecipe(getRecipeRequest());
    assertThat(recipe).isNotNull()
        .usingRecursiveComparison()
        .comparingOnlyFields(NAME, IS_VEG, SERVINGS, INGREDIENTS, INSTRUCTIONS);
  }

  @Test
  @DisplayName("when Recipe is passed, then it should be mapped to RecipeResponse object")
  void shouldMapGetRecipeById() {
    RecipeResponse recipeResponse = recipeMapper.mapGetRecipeById(getRecipe());
    assertThat(recipeResponse).isNotNull()
        .usingRecursiveComparison()
        .comparingOnlyFields(NAME, IS_VEG, SERVINGS, INGREDIENTS, INSTRUCTIONS);
  }

  @Test
  @DisplayName("when RecipeRequest is passed, then it should be mapped to Recipe object")
  void shouldMapUpdateRecipe() {
    Recipe recipe = recipeMapper.mapUpdateRecipe(getRecipeRequest(), getRecipe());
    assertThat(recipe).isNotNull()
        .usingRecursiveComparison()
        .comparingOnlyFields(NAME, IS_VEG, SERVINGS, INGREDIENTS, INSTRUCTIONS);
  }
}
