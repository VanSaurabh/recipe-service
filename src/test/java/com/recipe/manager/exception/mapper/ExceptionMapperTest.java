package com.recipe.manager.exception.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.exception.model.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExceptionMapperTest {

  @InjectMocks
  private ExceptionMapperImpl exceptionMapper;

  @Test
  void shouldMapNotFoundException() {
    NotFoundException notFoundException = new NotFoundException("not found");
    ExceptionResponse response = exceptionMapper
        .mapNotFoundException(notFoundException, NOT_FOUND.value());

    assertThat(response).isNotNull();
    assertThat(response.getMessage()).isEqualTo("not found");
    assertThat(response.getErrorCode()).isEqualTo(String.valueOf(NOT_FOUND.value()));
  }
}
