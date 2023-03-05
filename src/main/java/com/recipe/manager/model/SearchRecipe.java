package com.recipe.manager.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecipe {

  private Boolean isVeg;
  private Integer serving;
  private List<String> includedIngredients;
  private List<String> excludedIngredients;
  private String searchInstructions;
}
