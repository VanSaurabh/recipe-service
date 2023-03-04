/*
package com.recipe.manager.repository;

import com.recipe.manager.entity.RecipeEntity;
import java.util.List;
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

  List<RecipeEntity> getRecipesBySearchCriteria() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<RecipeEntity> criteriaQuery = criteriaBuilder.createQuery(RecipeEntity.class);

    Root<RecipeEntity> recipes = criteriaQuery.from(RecipeEntity.class);
    Predicate authorNamePredicate = criteriaBuilder.equal(book.get("author"), authorName);
    Predicate titlePredicate = cb.like(book.get("title"), "%" + title + "%");
    cq.where(authorNamePredicate, titlePredicate);
  }
}
*/
