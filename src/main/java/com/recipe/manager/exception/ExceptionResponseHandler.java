package com.recipe.manager.exception;

import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.mapper.RecipeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionResponseHandler {

  private final RecipeMapper recipeMapper;

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CommonException> handleNotFoundException(NotFoundException notFoundException) {
    CommonException exception = recipeMapper.mapNotFoundException(notFoundException, HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
  }
}
