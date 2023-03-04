package com.recipe.manager.repository;

import static java.util.Objects.nonNull;

import com.recipe.manager.entity.Recipe;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeSearchRepository {

  private final EntityManager entityManager;

  public Optional<List<Recipe>> getRecipesBySearchCriteria(Boolean isVeg, Integer serving,
      List<String> includedIngredients, List<String> excludedIngredients,
      String searchInstructions) {

    StringBuilder queryBuilder = new StringBuilder("SELECT rec FROM Recipe rec WHERE");
    if(nonNull(isVeg)) {
      queryBuilder.append(" rec.isVegetarian = ").append(isVeg);
    }

    if(nonNull(serving)) {
      queryBuilder.append(" and rec.servings = ").append(serving);
    }

    if(nonNull(includedIngredients) && !includedIngredients.isEmpty()) {
      queryBuilder.append(" and ( rec.ingredients like '%").append(includedIngredients.get(0)).append("%'");
      for(int i=1; i<includedIngredients.size(); i++) {
        queryBuilder.append(" or rec.ingredients like '%").append(includedIngredients.get(i)).append("%'");
      }
      queryBuilder.append(")");
    }

    if(nonNull(excludedIngredients) && !excludedIngredients.isEmpty()) {
      queryBuilder.append(" and ( rec.ingredients not like '%").append(excludedIngredients.get(0)).append("%'");
      for(int i=1; i<excludedIngredients.size(); i++) {
        queryBuilder.append(" and rec.ingredients not like '%").append(excludedIngredients.get(i)).append("%'");
      }
      queryBuilder.append(")");
    }

    if(nonNull(searchInstructions)) {
      queryBuilder.append(" and rec.instructions like '%").append(searchInstructions).append("%");
    }

    queryBuilder.append(" and rec.isDeleted = ").append("false");

    TypedQuery<Recipe> query = entityManager
        .createQuery(queryBuilder.toString(), Recipe.class);

    return Optional.ofNullable(query.getResultList());
  }
}
