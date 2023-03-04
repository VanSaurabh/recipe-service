package com.recipe.manager.repository;

import static com.recipe.manager.util.TestUtil.getRecipe;
import static com.recipe.manager.util.TestUtil.getRecipe2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.recipe.manager.entity.Recipe;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
  @Captor
  private ArgumentCaptor<String> stringArgumentCaptor;

  private final Recipe recipe1 = getRecipe();
  private final Recipe recipe2 = getRecipe2();

  @BeforeEach
  public void setup() {
    TypedQuery typedQuery = Mockito.mock(TypedQuery.class);

    when(entityManager.createQuery(anyString(), any()))
        .thenReturn(typedQuery);
    when(typedQuery.getResultList())
        .thenReturn(List.of(recipe1, recipe2));
  }

  @Test
  @DisplayName("when all the valid search criterias are passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByAllCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(true, 4, List.of("tomatoes", "ginger"),
            List.of("Liccorice", "sugar"), "cook, peel");

    verify(entityManager).createQuery(stringArgumentCaptor.capture(), any());
    String query = stringArgumentCaptor.getValue();

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    assertThat(query)
        .isEqualTo("SELECT rec FROM Recipe rec WHERE rec.isVegetarian = true "
            + "and rec.servings = 4 and ( rec.ingredients like '%tomatoes%' or rec.ingredients "
            + "like '%ginger%') and ( rec.ingredients not like '%Liccorice%' and rec.ingredients "
            + "not like '%sugar%') and rec.instructions like '%cook, peel% and rec.isDeleted = false");
  }

  @Test
  @DisplayName("when only isVeg search criteria is passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByOnlyIsVegCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(true, 0,
            null, null, null);

    verify(entityManager).createQuery(stringArgumentCaptor.capture(), any());
    String query = stringArgumentCaptor.getValue();

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    assertThat(query)
        .isEqualTo("SELECT rec FROM Recipe rec WHERE "
            + "rec.isVegetarian = true and rec.isDeleted = false");
  }

  @Test
  @DisplayName("when only excluded ingredients search criteria is passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByOnlyExcludedIngredientsCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(true, 0, null,
            List.of("Liccorice", "sugar"), null);

    verify(entityManager).createQuery(stringArgumentCaptor.capture(), any());
    String query = stringArgumentCaptor.getValue();

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    assertThat(query)
        .isEqualTo("SELECT rec FROM Recipe rec WHERE rec.isVegetarian = true "
            + "and ( rec.ingredients not like '%Liccorice%' and rec.ingredients not like '%sugar%') "
            + "and rec.isDeleted = false");
  }

  @Test
  @DisplayName("when only included ingredients search criteria is passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByOnlyIncludedIngredientsCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(true, 0, List.of("tomatoes", "ginger"),
            null, null);

    verify(entityManager).createQuery(stringArgumentCaptor.capture(), any());
    String query = stringArgumentCaptor.getValue();

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    assertThat(query)
        .isEqualTo("SELECT rec FROM Recipe rec WHERE rec.isVegetarian = true "
            + "and ( rec.ingredients like '%tomatoes%' or rec.ingredients like '%ginger%') "
            + "and rec.isDeleted = false");
  }

  @Test
  @DisplayName("when only search instructions are passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByOnlySearchInstructionsCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(true, 0, null,
            null, "cook, peel");

    verify(entityManager).createQuery(stringArgumentCaptor.capture(), any());
    String query = stringArgumentCaptor.getValue();

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    assertThat(query)
        .isEqualTo("SELECT rec FROM Recipe rec WHERE rec.isVegetarian = true "
            + "and rec.instructions like '%cook, peel% and rec.isDeleted = false");
  }

  @Test
  @DisplayName("when only servings are passed, "
      + "then return the list of recipes")
  void shouldSearchRecipeByOnlyServingsCriteria() {
    Optional<List<Recipe>> optionalRecipes = recipeSearchRepository
        .getRecipesBySearchCriteria(true, 4, null,
            null, null);

    verify(entityManager).createQuery(stringArgumentCaptor.capture(), any());
    String query = stringArgumentCaptor.getValue();

    assertThat(optionalRecipes).isPresent().get().isEqualTo(List.of(recipe1, recipe2));
    assertThat(query)
        .isEqualTo("SELECT rec FROM Recipe rec WHERE rec.isVegetarian = true "
            + "and rec.servings = 4 and rec.isDeleted = false");
  }

}
