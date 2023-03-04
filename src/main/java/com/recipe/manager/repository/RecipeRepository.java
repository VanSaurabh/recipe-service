package com.recipe.manager.repository;

import com.recipe.manager.entity.RecipeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {

  Optional<List<RecipeEntity>> findAllByIsDeleted(boolean isDeleted);

}
