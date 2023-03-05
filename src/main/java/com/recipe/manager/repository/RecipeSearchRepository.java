package com.recipe.manager.repository;

import static java.util.Objects.nonNull;

import com.recipe.manager.entity.Recipe;
import com.recipe.manager.model.SearchRecipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeSearchRepository {

  private final EntityManager entityManager;

  public Optional<List<Recipe>> getRecipesBySearchCriteria(SearchRecipe searchRecipe) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
    Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

    List<Predicate> predicates = new ArrayList<>();

    createIsVegPredicate(searchRecipe, criteriaBuilder, recipeRoot, predicates);
    createServingsPredicate(searchRecipe, criteriaBuilder, recipeRoot, predicates);
    createIncludedIngredientsPredicate(searchRecipe, criteriaBuilder, recipeRoot, predicates);
    createExcludedIngredientsPredicate(searchRecipe, criteriaBuilder, recipeRoot, predicates);
    createInstructionPredicate(searchRecipe, criteriaBuilder, recipeRoot, predicates);

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getResultList());
  }

  private static void createInstructionPredicate(SearchRecipe searchRecipe,
      CriteriaBuilder criteriaBuilder,
      Root<Recipe> recipeRoot, List<Predicate> predicates) {
    if (searchRecipe.getSearchInstructions() != null && !searchRecipe.getSearchInstructions().isEmpty()) {
      predicates.add(
          criteriaBuilder.like(recipeRoot.get("instructions"), "%" + searchRecipe.getSearchInstructions() + "%"));
    }
  }

  private static void createExcludedIngredientsPredicate(SearchRecipe searchRecipe,
      CriteriaBuilder criteriaBuilder,
      Root<Recipe> recipeRoot, List<Predicate> predicates) {
    if (nonNull(searchRecipe.getExcludedIngredients()) && !searchRecipe.getExcludedIngredients().isEmpty()) {
      List<Predicate> excludedPredicate = new ArrayList<>();

      for (String excludedIngredient : searchRecipe.getExcludedIngredients()) {
        excludedPredicate.add(
            criteriaBuilder.notLike(recipeRoot.get("ingredients"), "%" + excludedIngredient + "%"));
      }

      predicates.add(criteriaBuilder.and(excludedPredicate.toArray(new Predicate[0])));
    }
  }

  private static void createIncludedIngredientsPredicate(SearchRecipe searchRecipe,
      CriteriaBuilder criteriaBuilder,
      Root<Recipe> recipeRoot, List<Predicate> predicates) {
    if (nonNull(searchRecipe.getIncludedIngredients()) && !searchRecipe.getIncludedIngredients().isEmpty()) {
      List<Predicate> includedPredicate = new ArrayList<>();

      for (String includedIngredient : searchRecipe.getIncludedIngredients()) {
        includedPredicate.add(
            criteriaBuilder.like(recipeRoot.get("ingredients"), "%" + includedIngredient + "%"));
      }

      predicates.add(criteriaBuilder.or(includedPredicate.toArray(new Predicate[0])));
    }
  }

  private static void createServingsPredicate(SearchRecipe searchRecipe,
      CriteriaBuilder criteriaBuilder,
      Root<Recipe> recipeRoot, List<Predicate> predicates) {
    if (searchRecipe.getServing() != null) {
      predicates.add(criteriaBuilder.equal(recipeRoot.get("servings"), searchRecipe.getServing()));
    }
  }

  private static void createIsVegPredicate(SearchRecipe searchRecipe,
      CriteriaBuilder criteriaBuilder,
      Root<Recipe> recipeRoot, List<Predicate> predicates) {
    if (searchRecipe.getIsVeg() != null) {
      predicates.add(criteriaBuilder.equal(recipeRoot.get("isVegetarian"), searchRecipe.getIsVeg()));
    }
  }
}
