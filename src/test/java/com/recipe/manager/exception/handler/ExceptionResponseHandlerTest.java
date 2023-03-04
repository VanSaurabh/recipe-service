package com.recipe.manager.exception.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.recipe.manager.exception.RecipeException.NotFoundException;
import com.recipe.manager.exception.mapper.ExceptionMapper;
import com.recipe.manager.exception.mapper.ExceptionMapperImpl;
import com.recipe.manager.exception.model.ExceptionResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ExceptionResponseHandlerTest {

  @InjectMocks
  private ExceptionResponseHandler exceptionResponseHandler;
  @Mock
  private ExceptionMapperImpl exceptionMapper;

  @Test
  void shouldHandleNotFoundException() {
    NotFoundException notFoundException = new NotFoundException("not found");
    ExceptionResponse response = ExceptionResponse.builder()
        .message("not found")
        .errorCode(NOT_FOUND.toString())
        .build();

    when(exceptionMapper.mapNotFoundException(any(), anyInt()))
        .thenReturn(response);

    ResponseEntity<ExceptionResponse> responseEntity = exceptionResponseHandler
        .handleNotFoundException(notFoundException);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getMessage()).isEqualTo("not found");
  }

}
