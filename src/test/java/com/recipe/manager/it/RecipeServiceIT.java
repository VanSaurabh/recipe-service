package com.recipe.manager.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.manager.RecipeServiceApplication;
import com.recipe.manager.exception.model.ExceptionResponse;
import com.recipe.manager.server.model.RecipeRequest;
import com.recipe.manager.server.model.RecipeResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ActiveProfiles("it")
@SpringBootTest(classes = RecipeServiceApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RecipeServiceIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private static final String RECIPE_MANAGER_URL = "/recipe-manager/api/v1/recipes/";
  private static final String RECIPE_SEARCH_URL = "/recipe-manager/api/v1/search/recipes/";

  @Test
  @DisplayName("when valid request to get all recipes is passed, "
      + "then all valid recipes should be returned")
  void shouldGetRecipes() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_MANAGER_URL))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(10);
  }

  @Test
  @DisplayName("when valid request to get recipes by id is passed, "
      + "then recipe with the corresponding id should be returned")
  void shouldGetRecipesById() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_MANAGER_URL + "1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    RecipeResponse recipeResponse = objectMapper
        .readValue(result.getResponse().getContentAsString(), RecipeResponse.class);
    assertThat(recipeResponse).isNotNull();
    assertThat(recipeResponse.getId()).isEqualTo(1);
    assertThat(recipeResponse.getName()).isEqualTo("spaghetti carbonara");
  }

  @Test
  @DisplayName("when invalid request to get recipes by id is passed, "
      + "then not found exception should be thrown")
  void shouldReturnNotFoundExceptionWhenInvalidIdIsPassedToGetRecipesById() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_MANAGER_URL + "11"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn();
    ExceptionResponse exceptionResponse = objectMapper
        .readValue(result.getResponse().getContentAsString(), ExceptionResponse.class);
    assertThat(exceptionResponse).isNotNull();
    assertThat(exceptionResponse.getErrorCode()).isEqualTo(String.valueOf(NOT_FOUND.value()));
    assertThat(exceptionResponse.getMessage()).isEqualTo("Recipe not found with id: 11");
  }

  @Test
  @DisplayName("when valid request to delete recipes by id is passed, "
      + "then recipe with the corresponding id should be deleted")
  void shouldDeleteRecipesById() throws Exception {
    mockMvc.perform(delete(RECIPE_MANAGER_URL + "2"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();
  }

  @Test
  @DisplayName("when valid request to add a recipe is passed, "
      + "then recipe should be added")
  void shouldAddRecipes() throws Exception {
    RecipeRequest recipeRequest = new RecipeRequest()
        .name("Pasta - No Pasta")
        .isVegetarian(true)
        .servings(2)
        .ingredients("Floor, pasta sauce, milk, oregano")
        .instructions("Heat the pan, add milk and create the sauce, then add oregano and pasta");

    mockMvc.perform(post(RECIPE_MANAGER_URL)
            .header("content-type", "application/json")
            .content(objectMapper.writeValueAsString(recipeRequest)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();

    MvcResult result = mockMvc.perform(get(RECIPE_MANAGER_URL))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(11);
  }

  @Test
  @DisplayName("when valid request to update recipes by id is passed, "
      + "then recipe with the corresponding id should be updated")
  void shouldUpdateRecipes() throws Exception {
    MvcResult responseResult = mockMvc.perform(get(RECIPE_MANAGER_URL + "3"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    RecipeResponse recipeResponse = objectMapper
        .readValue(responseResult.getResponse().getContentAsString(), RecipeResponse.class);

    RecipeRequest recipeRequest = new RecipeRequest()
        .name(recipeResponse.getName())
        .instructions(recipeResponse.getInstructions())
        .servings(recipeResponse.getServings() + 3)
        .ingredients(recipeResponse.getIngredients() + "milk")
        .isVegetarian(true);

    mockMvc.perform(put(RECIPE_MANAGER_URL + "3")
            .header("content-type", "application/json")
            .content(objectMapper.writeValueAsString(recipeRequest)))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    MvcResult result = mockMvc.perform(get(RECIPE_MANAGER_URL + "3"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    RecipeResponse response = objectMapper
        .readValue(result.getResponse().getContentAsString(), RecipeResponse.class);
    assertThat(response).isNotNull();
    assertThat(response.getServings()).isEqualTo(recipeResponse.getServings() + 3);
    assertThat(response.getIngredients()).isEqualTo(recipeResponse.getIngredients() + "milk");
  }

  @Test
  @DisplayName("when valid request to search recipes by isVeg option is passed, "
      + "then all valid recipes should be returned")
  void shouldSearchAllVegRecipes() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL + "?isVeg=true"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(6);
  }

  @Test
  @DisplayName("when valid request to search recipes by servings is passed, "
      + "then all valid recipes should be returned")
  void shouldSearchAllRecipesWithFourServings() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL + "?serving=6"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(3);
  }

  @Test
  @DisplayName("when valid request to search recipes by included ingredients is passed, "
      + "then all valid recipes should be returned")
  void shouldSearchAllRecipesWithIncludedIngredients() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL + "?includedIngredients=tomatoes,carrots"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(4);
  }

  @Test
  @DisplayName("when valid request to search recipes by excluded ingredients is passed, "
      + "then all valid recipes should be returned")
  void shouldSearchAllRecipesWithExcludedIngredients() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL + "?excludedIngredients=lentils,celery"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(8);
  }

  @Test
  @DisplayName("when valid request to search recipes by instructions is passed, "
      + "then all valid recipes should be returned")
  void shouldSearchAllRecipesWithInstructions() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL + "?searchInstructions=Add garlic"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(3);
  }

  @Test
  @DisplayName("when valid request to search recipes is passed, "
      + "then all valid recipes should be returned")
  void shouldSearchAllRecipes() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL
            + "?isVeg=false"
            + "&?serving=4"
            + "&?includedIngredients=carrots"
            + "&?excludedIngredients=lentils"
            + "&searchInstructions=Heat"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(1);
  }

  @Test
  @DisplayName("when request to search recipes are all null, "
      + "then all valid recipes should be returned")
  void shouldSearchAllRecipesWhenNoSearchConditionIsNull() throws Exception {
    MvcResult result = mockMvc.perform(get(RECIPE_SEARCH_URL))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(10);
  }

}
