package com.recipe.manager.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.recipe.manager.entity.Recipe;
import com.recipe.manager.server.model.RecipeRequest;
import com.recipe.manager.server.model.RecipeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface RecipeMapper {

  Recipe mapCreateRecipe(RecipeRequest recipe);

  RecipeResponse mapGetRecipeById(Recipe recipeEntity);

  Recipe mapUpdateRecipe(RecipeRequest recipe, @MappingTarget Recipe recipeEntity);
}
