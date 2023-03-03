package com.recipe.manager.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.recipe.manager.entity.RecipeEntity;
import com.recipe.manager.server.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface RecipeMapper {

  RecipeEntity mapCreateRecipe(Recipe recipe);

  Recipe mapGetRecipeById(RecipeEntity recipeEntity);

  RecipeEntity mapUpdateRecipe(Recipe recipe, @MappingTarget RecipeEntity recipeEntity);
}
