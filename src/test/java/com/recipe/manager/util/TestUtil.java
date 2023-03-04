package com.recipe.manager.util;

import com.recipe.manager.entity.Recipe;
import com.recipe.manager.server.model.RecipeRequest;
import com.recipe.manager.server.model.RecipeResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {

  public static RecipeRequest getRecipeRequest() {
    return new RecipeRequest()
        .name("Biryani")
        .ingredients("Rice, chicken, Masala")
        .servings(4)
        .isVegetarian(false)
        .instructions("Cook chicken and rice, mix them");
  }

  public static Recipe getRecipe2() {
    return Recipe.builder()
        .id(2)
        .name("Daal Baati")
        .ingredients("Daal, Baati, Chokha, Chutney")
        .servings(2)
        .isVegetarian(true)
        .isDeleted(false)
        .instructions("Cook daal, cook baati, add chutney and serve")
        .build();
  }

  public static Recipe getRecipe() {
    return Recipe.builder()
        .id(1)
        .name("Biryani")
        .ingredients("Rice, chicken, Masala")
        .servings(4)
        .isVegetarian(false)
        .isDeleted(false)
        .instructions("Cook chicken and rice, mix them")
        .build();
  }

  public static RecipeResponse getRecipeResponse() {
    return new RecipeResponse()
        .id(1)
        .name("Biryani")
        .ingredients("Rice, chicken, Masala")
        .servings(4)
        .isVegetarian(false)
        .instructions("Cook chicken and rice, mix them");
  }

  public static RecipeResponse getRecipeResponse2() {
    return new RecipeResponse()
        .id(2)
        .name("Daal Baati")
        .ingredients("Daal, Baati, Chokha, Chutney")
        .servings(2)
        .isVegetarian(true)
        .instructions("Cook daal, cook baati, add chutney and serve");
  }
}
