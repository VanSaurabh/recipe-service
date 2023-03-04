package com.recipe.manager.exception.handler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.exception.mapper.ExceptionMapper;
import com.recipe.manager.exception.model.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionResponseHandler {

  private final ExceptionMapper exceptionMapper;

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException notFoundException) {
    log.error("handling notFoundException", notFoundException);
    ExceptionResponse exception = exceptionMapper.mapNotFoundException(notFoundException, NOT_FOUND.value());
    return new ResponseEntity<>(exception, NOT_FOUND);
  }
}
