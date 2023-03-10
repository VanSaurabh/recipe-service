package com.recipe.manager.repository;

import static com.recipe.manager.util.TestUtil.getRecipe;
import static com.recipe.manager.util.TestUtil.getRecipe2;
import static com.recipe.manager.util.TestUtil.getSearchRecipe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.recipe.manager.entity.Recipe;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeSearchRepositoryTest {

  @InjectMocks
  private RecipeSearchRepository recipeSearchRepository;
  @Mock
  private EntityManager entityManager;
  @Mock
  private CriteriaBuilder criteriaBuilder;
  @Mock
  private CriteriaQuery<Recipe> criteriaQuery;
  @Mock
  private Root<Recipe> recipeRoot;

  private final Recipe recipe1 = getRecipe();
  private final Recipe recipe2 = getRecipe2();

  @BeforeEach
  public void setup() {
    when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    when(criteriaBuilder.createQuery(Recipe.class)).thenReturn(criteriaQuery);
    when(criteriaQuery.from(Recipe.class)).thenReturn(recipeRoot);

    TypedQuery typedQuery = Mockito.mock(TypedQuery.class);
    when(entityManager.createQuery(criteriaQuery))
        .thenReturn(typedQuery);
    when(typedQuery.getResultList())
        .thenReturn(List.of(recipe1, recipe2));
  }

  @Test
  @DisplayName("when all the valid search criterias are passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByAllCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(getSearchRecipe(true, 4,
            List.of("tomatoes", "ginger"), List.of("Liccorice", "sugar"), "cook, peel"));

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    verify(entityManager, times(1)).getCriteriaBuilder();
    verify(criteriaBuilder, times(1)).createQuery(Recipe.class);
    verify(criteriaQuery, times(1)).from(Recipe.class);
  }

  @ParameterizedTest
  @MethodSource("generateData")
  @DisplayName("when different search criteria are passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByOnlyIsVegCriteria(boolean isVeg, int servings, List<String> included,
      List<String> excluded, String instructions) {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(getSearchRecipe(isVeg, servings,
            included, excluded, instructions));

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    verify(entityManager, times(1)).getCriteriaBuilder();
    verify(criteriaBuilder, times(1)).createQuery(Recipe.class);
    verify(criteriaQuery, times(1)).from(Recipe.class);
  }

  static Stream<Arguments> generateData() {
    return Stream.of(
        Arguments.of(true, 0, null, null, null),
        Arguments.of(true, 0, null, List.of("Liccorice", "sugar"), null),
        Arguments.of(true, 0, List.of("tomatoes", "ginger"), null, null),
        Arguments.of(true, 0, null, null, "cook, peel"),
        Arguments.of(true, 4, null, null, null));
  }
}
