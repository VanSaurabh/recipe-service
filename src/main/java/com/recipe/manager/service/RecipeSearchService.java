package com.recipe.manager.service;

import com.recipe.manager.mapper.RecipeMapper;
import com.recipe.manager.repository.RecipeSearchRepository;
import com.recipe.manager.server.model.Recipe;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeSearchService {

  private final RecipeMapper recipeMapper;

}
