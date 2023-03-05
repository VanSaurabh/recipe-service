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

  @Test
  void shouldGetRecipes() throws Exception {
    MvcResult result = mockMvc.perform(get("/recipe-manager/api/v1/recipes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(10);
  }

  @Test
  void shouldGetRecipesById() throws Exception {
    MvcResult result = mockMvc.perform(get("/recipe-manager/api/v1/recipes/1"))
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
  void shouldReturnNotFoundExceptionWhenInvalidIdIsPassedToGetRecipesById() throws Exception {
    MvcResult result = mockMvc.perform(get("/recipe-manager/api/v1/recipes/11"))
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
  void shouldDeleteRecipesById() throws Exception {
    mockMvc.perform(delete("/recipe-manager/api/v1/recipes/2"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();
  }

  @Test
  void shouldAddRecipes() throws Exception {
    RecipeRequest recipeRequest = new RecipeRequest()
        .name("Pasta - No Pasta")
        .isVegetarian(true)
        .servings(2)
        .ingredients("Floor, pasta sauce, milk, oregano")
        .instructions("Heat the pan, add milk and create the sauce, then add oregano and pasta");

    mockMvc.perform(post("/recipe-manager/api/v1/recipes")
            .header("content-type", "application/json")
            .content(objectMapper.writeValueAsString(recipeRequest)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();

    MvcResult result = mockMvc.perform(get("/recipe-manager/api/v1/recipes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    List<RecipeResponse> recipeResponses = objectMapper
        .readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
    assertThat(recipeResponses).isNotNull().isNotEmpty().hasSize(11);
  }

  @Test
  void shouldUpdateRecipes() throws Exception {
    MvcResult responseResult = mockMvc.perform(get("/recipe-manager/api/v1/recipes/3"))
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

    mockMvc.perform(put("/recipe-manager/api/v1/recipes/3")
            .header("content-type", "application/json")
            .content(objectMapper.writeValueAsString(recipeRequest)))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    MvcResult result = mockMvc.perform(get("/recipe-manager/api/v1/recipes/3"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    RecipeResponse response = objectMapper
        .readValue(result.getResponse().getContentAsString(), RecipeResponse.class);
    assertThat(response).isNotNull();
    assertThat(response.getServings()).isEqualTo(recipeResponse.getServings() + 3);
    assertThat(response.getIngredients()).isEqualTo(recipeResponse.getIngredients() + "milk");
  }

}
